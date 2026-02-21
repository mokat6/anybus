import { faArrowRightLong, faRightLeft } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react"
import { useParams, useSearchParams } from "react-router-dom";
import { apiGetSchedulesByLineBrowse } from "services/user.service";
import { dateRegex, generateDaysOfWeek, validateInteger } from "utils/myUtils";
import BsBubbles from "./BsBubbles";

const findRuleToday = (arr, queryDate) => {
    for (const rule of arr) {
        for (const dateRange of rule.timePeriods) {
            const startDate = new Date(queryDate.getFullYear(), dateRange.startMonth - 1, dateRange.startDay);
        const endDate = new Date(queryDate.getFullYear(), dateRange.endMonth - 1, dateRange.endDay);
        const isWithinRange = startDate <= queryDate && queryDate <= endDate;
        if (isWithinRange) return [rule.periodName]

        }
        
   }
    return [];
}

const makeDateObjFromStrOrEmpty = (dateStr) => {
    return dateRegex.test(dateStr) ? new Date(dateStr) : new Date();
}

const validateDir = (dir) => {
    switch (dir) {
        case 'City bound':
          return dir
          break;
        default:
          return 'Out bound'
      }
}

export default function ViewLineSchedules(){
    const [searchParams] = useSearchParams();
    const [data, setData] = useState();
    const [line, setLine] = useState();
    const {lineId, schedId, tripId} = useParams();
    const [dir, setDir] = useState(validateDir(searchParams.get('dir')));
    const [flipped, setFlipped] = useState(false);
    const [selectedSchedule, setSelectedSchedule] = useState(0);
    const [ruleFilter, setRuleFilter] = useState([]); //all checked values appear in the array, multiple selection
    const [allYearlyRules, setAllYearlyRules] = useState();
    
    //query params, from header filters
    const [queryDate, setQueryDate] = useState(makeDateObjFromStrOrEmpty(searchParams.get('date')))
    const [qdir, setQdir] = useState(validateDir(searchParams.get('dir')));
    const [qbsFrom, setQbsFrom] = useState(validateInteger(searchParams.get('from')))

    const [noScheduleError, setNoScheduleError] = useState();

    // const [query]

    console.log("TIRP ID: ", tripId)

    const logState = () => {
        console.log("data > ", data);
        console.log("line > ", line);  
        console.log("generate curr trip table data > ", generateTableData())  
        console.log("dir > ", dir)
        console.log("all yearly rules", allYearlyRules);
        console.log("rule filters", ruleFilter);
        console.log("selectedSchedule > ", selectedSchedule);
    }





    useEffect(()=>{
        apiGetSchedulesByLineBrowse(lineId).then(response => {
            if (response.data.nothingFound) {
                setNoScheduleError ("This line has no schedules, inform administration")
                return;
            }
            const schedules = response.data.schedules;

            if (schedules[0].trips.length === 0) {
                setNoScheduleError("The line is broken, schedule has no trips")
                return;
            }

            //if user clicked on "City bound" schedule item in the 'Timetable page' then flip the directioni arrow
            if (schedId && tripId) {
                const entryTripItem = schedules.find(sched => sched.id === validateInteger(schedId)).trips.find(trip => trip.id === validateInteger(tripId));
                if ( entryTripItem.boundFor === "City bound"){
                    setFlipped(true)
                    console.log("YES I DID IT I FLIPPED THE X");
                    console.log("DIR IS", dir)
                }
                    
            }
         

            //first sort, later setSelectedSchedule
            schedules.sort((a,b) => {
                let a1; let b1;
                if (a.trips.length === 1) a1 = a.trips[0].timeList[0]?.split(":");
                if (b.trips.length === 1) b1 = b.trips[0].timeList[0]?.split(":");

                if (a.trips.length === 2) a1 = a.trips.find(trip=>trip.boundFor === dir)?.timeList[0].split(":")
                if (b.trips.length === 2) b1 = b.trips.find(trip=>trip.boundFor === dir)?.timeList[0].split(":")
                if (!a1 || !b1 ) return 1;
                return (a1[0] * 60 + a1[1]) - (b1[0] * 60 + b1[1])
            })

            if (response.data.line.enabledSeasonalYearlyRuleFilter)
                setRuleFilter(findRuleToday(response.data.yearlyRules, queryDate))
            
            if (schedId) setSelectedSchedule( schedules.findIndex(sched => sched.id === validateInteger(schedId)) )
            setAllYearlyRules(response.data.yearlyRules)
            setData(schedules)
            setLine(response.data.line)
        })            
    }, [])

    const handleFlipDirection = () => {
        setDir(og=> og === "Out bound" ? "City bound" : "Out bound")
        setFlipped(!flipped)
    }

    const generateTableData = () => {
        const trip = getTripBySelectedDirOrFirst(data[selectedSchedule]);
        if (trip.routeDirReversed) trip.route.stopsArr.reverse()
        return trip.route.stopsArr.map((stop, index) => ({
                stop: stop, 
                time: trip.timeList[index].slice(0,5),
                highlight: stop.id === validateInteger(qbsFrom) && dir === qdir ? true : false
            }))
    }

    const handleChangeSchedFilter = (e) => {
        const {value, checked} = e.target;
        console.log("value is___" , value)
        console.log("checked is___" , checked)
    
        let filterCopy = [... ruleFilter];
        if (checked) 
            filterCopy.push(value);
        else
            filterCopy = filterCopy.filter(rule => rule !== value)
    
        setRuleFilter(filterCopy)
    }

    const getTripBySelectedDirOrFirst = (schedule) => {
        if (schedule.trips.length === 2){
            console.log("2!!!!!!!!!", dir);
            return schedule.trips.find(trip => trip.boundFor === dir)            
        }

        if (schedule.trips.length ===1){
            console.log("1!!!!!!!!!", schedule.trips[0]);            
            return schedule.trips[0]
        }

        }
    
    if (noScheduleError) return <main>{noScheduleError}</main>
    if (!data || !line || !dir || !allYearlyRules) return <main>loading...</main>
    return(
        <main className="container" onClick={logState} >
            <div className="row">
            <div className="mygrid-container">
                
                {/* .mygrid-menu */}
                <div className="mygrid-menu">
                    <h1 className="text-center"> {line.name} </h1>
                    
                    {/* filter for filtering by yearly rule - e.g. winter/summer */}
                    {
                    line.enabledSeasonalYearlyRuleFilter &&
                    <div className="bg-dark text-light p-2 mb-2">
                        {  
                            allYearlyRules.map(rule=>rule.periodName).map((rule, index) => (
                                <div className="form-check form-check-inline" key={rule}>
                                    <label className="form-check-label">
                                    <input 
                                        className="form-check-input" 
                                        type="checkbox" 
                                        name="YearlyRuleFilter"
                                        value={rule} 
                                        checked={ruleFilter.includes(rule)}
                                        onChange={(e) => handleChangeSchedFilter(e)}
                                    />
                                   {rule}
                                 </label>
                              </div>
                            ))
                        }
                    </div>
                    }

                    <ul className="list-group">
                        {
                        data.filter(sched=>{
                            if (!line.enabledSeasonalYearlyRuleFilter) return true;
                            return ruleFilter.includes(sched.runsOnYearlyStr)
                        }).map((schedule, index) => (
                        <li key={schedule.id} className={`list-group-item d-flex gap-4 
                        
                        ${schedule.id === data[selectedSchedule].id ? "list-group-item-primary" : ""}
                        
                        `} onClick={()=> setSelectedSchedule(  data.findIndex(sched => sched.id === schedule.id)  )}>
                            <span className="my-layout-shift-fix" style={{minWidth:"3rem"}}>
                                {getTripBySelectedDirOrFirst(schedule).timeList[0].slice(0,5)}
                            </span>
                            <span>{generateDaysOfWeek(schedule.runsOnWeekly)}</span>
                            <span>{schedule.runsOnYearlyStr === "Apskritus metus" ? "-" : schedule.runsOnYearlyStr}</span>
                        </li>
                        ))                        
                        }  
                    </ul>
                </div>
                
                {/* .mygrid-list */}                
                <div className="mygrid-list">
                    {
                    line.routeType === "City Bus"
                    ?
                    <div className="fs-3 text-center"> {line.routeType} </div>
                    :
                    <div className="d-flex align-items-center">
                        <div style={{ flex: 1 }} className="fs-3 text-center text-md-end pe-md-4">{line.routeStart}</div>
                        <button className={`btn`} disabled={new Set(data[selectedSchedule].trips.map(trip => trip.boundFor)).size < 2}
                             onClick={handleFlipDirection}><FontAwesomeIcon icon={faArrowRightLong} className={`flip-animation ${flipped ? 'flipped' : ''}`} /></button>
                        <div style={{ flex: 1 }} className="fs-3 text-center text-md-start ps-md-4">{line.routeEnd}</div>
                    </div>            
                    }    
                    <div className="px-4 d-flex align-items-center flex-column">            
                            <span className="fs-4 my-3 text-secondary">{getTripBySelectedDirOrFirst(data[selectedSchedule]).route.routeNotes}</span>
                            
                            <BsBubbles data={generateTableData()} />
                    </div>
                 </div>

                {/* .mygrid-info */}
                <div className="mygrid-info"> 
                    <table className="table table-light" >
                        <thead></thead>
                        <tbody>
                            <tr><td>Runs on public holidays</td><td className="fw-bold">{data[selectedSchedule].runsOnPublicHolidays ? "Yes" : "No"}</td></tr>
                            <tr><td>Operator</td><td>{line.operator}</td></tr>
                            <tr><td>Anyksciai AS platform</td><td>{line.anykStationPlatform}</td></tr>
                            <tr><td>Scope</td><td>{line.routeType}</td></tr>
                            {
                                line.customNotes.map(note => (
                                    <tr key={note.id}>
                                        <td>{note.noteKey}</td>
                                        <td>{note.noteValue}</td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>
            </div></div>
        </main>
    )
}