import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";

import { apiGetSchedules, apiGetRoutesByLine, apiPostSchedules, apiDelScheduleById } from "services/user.service";
import { faArrowLeft, faRotateLeft, faPlus, faTrashCan} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import SchedulePickTable from "./components/SchedulePickTable";
import Timetable from "./components/Timetable";
import Constraints from "./components/Constraints";

//if they were within the default function, their value would reset after each re-render, not good
let newId = -6321;
let flagGimmeLastSchedule = false;
let timeoutId;
let typingDebounce = null;

export default function SchMain(){
    const {lineId, openScheduleId} = useParams();
    const [data, setData] = useState();
    const [lineTitle, setLineTitle] = useState();
    const [staticData, setStaticData] = useState();
    const [selectedSchedule, setSelectedSchedule] = useState(0);
    const [routes, setRoutes] = useState();
    const [showTopBarMsg, setShowTopBarMsg] = useState();
    const [validationOn, setValidationOn] = useState(false);
    const navigate = useNavigate();



     useEffect(() => {
        apiGetSchedules(lineId).then(response => {
            setData(response.data.data)
            
            //URL contains scheduleId -> set setSelectedSchedule to be it
            if (openScheduleId) {
                const index = response.data.data.findIndex(schedule => schedule.id == openScheduleId);
                if (index != -1) setSelectedSchedule(index)
            }

            setStaticData({
                "emptySchedule": response.data.empty,
                "boundForOptions": response.data.boundForOptions,
                "yearlyOptions": response.data.yearlyOptions
            })
            setLineTitle(response.data.lineTitle)
        });
        apiGetRoutesByLine(lineId).then(response => setRoutes(response.data));
    }, [])

const logDataFunk = () => {
    console.log("data", data);
    console.log("routes", routes);
    console.log("staticData.emptySchedule", staticData)
    console.log("selected schedule", selectedSchedule);
}

const handleRouteChange = (newRouteId, tripIndex) => {
    setData(og => {
        const newData = [...og];

        newData[selectedSchedule].trips[tripIndex].routeId = newRouteId;
        console.log("new data man", newData);
        return newData;
    })
}

const handleReverseStops = (val, tripIndex) => {
    console.log("reverse value is", val)
    setData(og => {
        const newData = [...og];
        newData[selectedSchedule].trips[tripIndex].routeDirReversed = val;
        return newData;
    })
}



const handleNewSchedule = () => {
    //add routes[0].id (first by default) to the schedule > trip 
    //deeper copy, avoids staticData mutation
    const empty = JSON.parse(JSON.stringify(staticData.emptySchedule));
    empty.id = newId--;
    empty.trips[0].routeId = routes[0].id;
    empty.trips[0].id = newId--;

    setData(og => [...og, empty])
    
    //combined with the following useEffect, helps change selectedSchedule to the newly created one
    flagGimmeLastSchedule = true;
    setValidationOn(false)

}
useEffect(() => {
    if (flagGimmeLastSchedule) {
        setSelectedSchedule(data.length - 1);
        flagGimmeLastSchedule = false;
    }
},[data])



const handleClickOpen = (index) => {
    setSelectedSchedule(index);

    let id = data[index].id;
    if (id >= 0)
        navigate(`/admin-panel/schedules/${lineId}/${id}`);
}

const handleChange = (newValue, path, key, debounceTime=200) =>{
    console.log("EXECUTING HERE")
    clearTimeout(typingDebounce);
    typingDebounce = setTimeout(() => {
  
        setData(ogData => {
            const newData = [...ogData];
            let currentStep = newData[selectedSchedule]; //walk the path, deeper and deeper

            if (path !== null && path !== undefined){
                for (let step of path)
                    currentStep = currentStep[step];
            }
            console.log("new year", newValue)

            currentStep[key] = newValue;
            return newData;
        });
    }, debounceTime); //debounce
}


const handleSubmit = () => {
    //make sure bus stops and time point numbers are equal
    const dataCopy = JSON.parse(JSON.stringify(data))

    dataCopy.forEach(schedule =>{
        schedule.trips.forEach(trip =>{
            const matchingRoute = routes.find(route => route.id === trip.routeId);
            trip.timeList = evenOutArrays(matchingRoute.stopsArr, trip.timeList);
        })
    })

    setData(dataCopy);
    
    //if big main validation fails

    if (!isAllDataValid(dataCopy)){
        setValidationOn(true);
        msgRun("Input validation failed, check if everything's entered right", "danger", 3000)
        return;
    }
    const boundForError = isBoundForInvalid(dataCopy);
    if (boundForError) {
        setValidationOn(true);
        msgRun(boundForError, "danger", 8000)
        return;
    }


    //validation passed, calling post api
    setValidationOn(false)
    //map negative IDs to null. only used in trips
    //why copy2? because  "setData(dataCopy)" is async and is executed after i nullify negative IDs, causing errors
    const dataCopy2 = JSON.parse(JSON.stringify(dataCopy))
    dataCopy2.forEach(schedule=> {
        if (schedule.id < 0 ) schedule.id = null;

        schedule.trips.forEach(trip => {
        if (trip.id < 0) trip.id = null;
    })
        if (!schedule.lineId) schedule.lineId = lineId;
})

    // dataCopy2[0].runsOnYearlyId = 2;
    // console.log("posting this -> ", dataCopy2)
    
    apiPostSchedules(dataCopy2).then(response => {
        setData(response.data)
        msgRun("Successfully saved to the database", "success", 3000)
    })
}

const msgRun = (msg, color, time) => {
    setShowTopBarMsg({msg:msg, color:color});
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
        setShowTopBarMsg();
    }, time); 
}

const isAllDataValid = (data) => {

    //each schedule , need index, to highlight failures
    for (let index = 0; index < data.length; index++) {
        let schedule = data[index]

    //constraints
        if (
            schedule.runsOnWeekly.length === 0 ||
            !schedule.runsOnYearlyId
            ) {
            return false;
        } 
    //each trip
        for (const trip of schedule.trips) {

    //each time
            for (const time of trip.timeList) {
                if (!time) 
                    return false;
            }
        
        }
    }

    return true;
}


const isBoundForInvalid = (data) => {
    //only valid when in a single trip schedule, the trip BoundFor is "One way"
    //                        two trip schedule, #1 trip is "Out bound" #2 trip is "City bound"
    const msg1 = "Input validation failed, In single trip schedules - trip direction must be 'One way' "
    const msg2 = "Input validation failed, In two trip schedules - 1 trip must be 'Out bound' and the other 'City bound'"
    const msg3 = "Min 1 trip per schedule, Max 2 trips per schedule"

    for (const schedule of data) {
        if (schedule.trips.length === 0) return msg3;
        if (schedule.trips.length === 1)
            if (schedule.trips[0].boundFor !== "One way") return msg1;
        
        if (schedule.trips.length === 2) {
            const boundForArr = schedule.trips.map(trip => trip.boundFor);
            if (!boundForArr.includes("Out bound") || !boundForArr.includes("City bound")) return msg2;
        }
        if (schedule.trips.length > 2 ) return msg3;
    }
    return false; //means not invalid :)
}

function evenOutArrays(arr1, arr2) {
    const lengthDiff = arr1.length - arr2.length;
    
    if (lengthDiff > 0) {
        // Second array is shorter, so add empty values to match length
        for (let i = 0; i < lengthDiff; i++) {
            arr2.push(null); // You can use any value you consider as empty
        }
    } else if (lengthDiff < 0) {
        // Second array is longer, so truncate it to match length
        arr2.splice(arr1.length);
    }
    
    return arr2;
}

const handleDeleteTrip = (delTrip) => {
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(data))
        dataCopy[selectedSchedule].trips = dataCopy[selectedSchedule].trips
                                            .filter(trip => trip.id !== delTrip.id)
        return dataCopy;
    })
}

const handleAddTrip = () => {
    if (data.length === 0) return
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(data))
        const id = newId--;

        console.log("static data here", staticData)
        // const emptyTrip = staticData.emptySchedule.trips[0];
        const emptyTrip = JSON.parse(JSON.stringify(staticData.emptySchedule.trips[0]));
        emptyTrip.id = id;
        emptyTrip.routeId = routes[0].id;        
        
        // console.log('pushing this ding', emptyTrip)
        dataCopy[selectedSchedule].trips
            .push(emptyTrip)

        return dataCopy;
    })
}

const handleDelSchedule = () => {
    setData(og => og.filter((schedule, index) => index != selectedSchedule  ))
    if (data[selectedSchedule].id > 0)
    apiDelScheduleById(data[selectedSchedule].id).then(response => {
        msgRun("The schedule has been deleted.", "success", 2000)
    })
    const selectedBefore = selectedSchedule;
    const schedCount = data.length;
    if (schedCount < 2) 
        setSelectedSchedule(0);
    else if (schedCount - 1 === selectedBefore) 
        setSelectedSchedule(og => og - 1)
}

    //data loading
    if (!data || !routes || !lineTitle) return <main>loading...</main>;
    
    //data loaded, but this line doesn't have any routes yet
    if (routes.length == 0) return <div className="mt-3">
    This line has no routes yet. Go create a new route with some bus stations first. 
    <Link to={`/admin-panel/lines/${lineId}`}
            className="btn btn-primary mx-2"
        >Add routes</Link>
    </div>

    //all good 
    return(
        <main className="row align-items-start pt-3 pb-3" onClick={logDataFunk}>
              {
                showTopBarMsg &&
                    <div className={`alert alert-${showTopBarMsg.color} `} role="alert">
                        {showTopBarMsg.msg}
                    </div>
               }

            <div className="d-flex justify-content-between mb-1">
                <h1 className="h3">{lineTitle}</h1>
                <div className="d-flex flex-wrap flex-grow-1 justify-content-end gap-3 align-content-start">
                    <Link to={`/admin-panel/lines/${lineId}`} className="btn btn-secondary">Go to Lines</Link>

                    <button className="btn btn-success btn-sm" onClick={handleSubmit}>Save</button>
                </div>
            </div>
            <div className="d-flex flex-wrap gap-4 mb-5">
                <SchedulePickTable data={data} className="" validationOn={validationOn}
                    handleNewSchedule={handleNewSchedule} routes={routes} handleClickOpen={handleClickOpen} 
                    selectedSchedule={selectedSchedule} />                

            </div>
            
            
            



<div className="d-flex justify-content-evenly align-content-start  flex-wrap">
            {data.length != 0 &&
                <Timetable routes={routes} schedule={data[selectedSchedule]} handleRouteChange={handleRouteChange}
                    selectedSchedule={selectedSchedule} handleReverseStops={handleReverseStops} handleChange={handleChange}
                    staticData={staticData} handleDeleteTrip={handleDeleteTrip}
                    className="col-md-8" validationOn={validationOn} />
            }

           {
                (data.length != 0) && 
                <div className="d-flex align-items-stretch" style={{marginTop: "100px",height:"200px"}}>
                    <button className="btn btn-light" onClick={handleAddTrip}>
                    <FontAwesomeIcon icon={faPlus} /></button>
                </div>
           }


            {data.length != 0 &&
                <div className="">
                    <div className="">


                    <button className="btn btn-danger" onClick={handleDelSchedule}>
                    <FontAwesomeIcon icon={faTrashCan} /> Del schedule</button>
                    </div>

                    <Constraints schedule={data[selectedSchedule]} handleChange={handleChange} 
                    yearlyOptions={staticData.yearlyOptions} data={data} 
                    validationOn={validationOn} />
                </div>
            }
            </div>
            
        </main>
    )
}