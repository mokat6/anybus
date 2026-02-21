import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import BusStopTableEdit from "./BusStopTableEdit";
import { useLocation } from 'react-router-dom';
import { apiPostLineEager, apiGetEmptyLine } from "services/user.service";
import { lineInfoLabel } from "utils/myUtils";
import { faTrashCan, faPlus, faCopy } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

let nextId = -1000;

export default function LinePageEdit(){
    const[data, setData] = useState();
    const[activeRouteIndexArr, setActiveRouteIndexArr] = useState([]);
    const {lineId} = useParams();
    let typingDebounce = null;
    const secondRowColor = "#dedede";
    const [validationOn, setValidationOn] = useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const [showScheduleLinks, setShowScheduleLinks] = useState(false);

    useEffect(() => {
        if (location.state) {
            setData(location.state)
            if (location.state.routes.length > 0)
                setActiveRouteIndexArr([0])
        }
            
        else 
        apiGetEmptyLine().then(response => setData(response.data))
    }, [])

const logState = () => {
    console.log(data);
}
    

    const handleSubmit = () => {
        setValidationOn(true);
        console.log("DATA", data);

        //Validation --
        // for (let key of Object.keys(data.info)) {
            // if (key !== "id" && !data.info[key]) return
        // }
        if (!data.info.name || !data.info.routeStart || !data.info.routeEnd || !data.info.routeType) return

        for (let note of data.info.customNotes) {
            if (!note.noteKey || !note.noteValue) return
        }

        for ( let route of data.routes) {
                if (!route.routeNotes) return;

            for (let stopObj of route.stopsArr){
                if (stopObj?.id == undefined) return;
            }
        }
        //ends validation --
        
        //nulify IDs
        data.info.customNotes.forEach(note => {
            if (note.id < 0) note.id = null;
        })

        //persist to DB
        
        console.log("validation passed");
        apiPostLineEager(data)
            .then(response => navigate(`/admin-panel/lines/${response.data || ""}`))
        
    }

    const handleRoutesClick = (id) => {
        const index = data.routes.findIndex(el => id === el.id);
        console.log(data);
        if (!activeRouteIndexArr.includes(index))
            setActiveRouteIndexArr(og => [...og, index]);
        else 
            setActiveRouteIndexArr(og => og.filter(i => i != index));

    }

    const handleChange = (newValue, path, key, debounceTime=200) =>{
        clearTimeout(typingDebounce);

        console.log("Actual data structure - ", data)
        console.log("DEBUG :: newValue", newValue)
        console.log("DEBUG :: path", path)
        console.log("DEBUG :: key", key)

        typingDebounce = setTimeout(() => {
      
            setData(ogData => {
                const newData = { ...ogData };
                let currentStep = newData; //walk the path, deeper and deeper


                if (path !== null && path !== undefined){
                    for (let step of path)
                        currentStep = currentStep[step];
                }

                console.log("current step", currentStep)
                    
                currentStep[key] = newValue;
                    

                return newData;
            });

        }, debounceTime); //debounce
    }


    const handleAddStop = (routeIndex, i, counterEl) => {
        console.log("+1", data, i);
        setData(og => {
            const newData = {...og}
            newData.routes[routeIndex].stopsArr.splice(i+1, 0, "")
            newData.routes[routeIndex].distanceMetersList.splice(i+1, 0, "")
            counterEl.current.value = newData.routes[routeIndex].stopsArr.length;
            return newData
        })
    }

    const handleRemoveStop = (routeIndex, i, counterEl) => {
        setData(og => {
            const newData = {...og}
            newData.routes[routeIndex].stopsArr.splice(i, 1)
            newData.routes[routeIndex].distanceMetersList.splice(i, 1)
            counterEl.current.value = newData.routes[routeIndex].stopsArr.length;
            return newData
        })    }

    const handleStopCountChange = (newValue, index) => {
        console.log("hehe", newValue);
        const lastDefinedIndex = data.routes[index].stopsArr.findLastIndex(stop=> stop.id)
        console.log("last defined", lastDefinedIndex);
        const surplus = newValue - lastDefinedIndex - 1;
        console.log("surplus", surplus)

        if (surplus >= 0)
            setData(og => {
                const newData = {...og};

                newData.routes[index].stopsArr.splice(lastDefinedIndex + 1)
                newData.routes[index].stopsArr.splice(lastDefinedIndex +1,0 ,  ...Array(surplus).fill({}))

                return newData
            })
    }

    const handleDeleteRoute = (route, index) => {
        setData(og => {
            const newData = {...og};
            newData.routes = newData.routes.filter(x=> x.id !== route.id);
            return newData;
        })

        //update activeRouteIndex
        let newActiveInd = [];
        activeRouteIndexArr.forEach(i=>{
            if (index > i)
                newActiveInd.push(i);
            else if (index < i)
                newActiveInd.push(i-1);
            //if equals, then do nothing > gets removed
        })
        setActiveRouteIndexArr(newActiveInd)
    }

    const handleNewRoute = () => {
        const emptyObject = {
            "id": nextId--,
            "stopsArr": [{}],
            "distanceMetersList": [],
            "routeNotes": ""
        };
        const routesLength = data.routes.length;
        setData(og => {
            const newData = {...og};
            newData.routes.push(emptyObject)
            return newData;
        })

        setActiveRouteIndexArr(og=>{
            return [...og, routesLength]
        })
    }
   
    const handleCopyRoute = (index) => {
        setData(og => {
            const newRoute = JSON.parse(JSON.stringify(data.routes[index]));
            newRoute.id = nextId--;
            newRoute.routeNotes = newRoute.routeNotes + " - copy"
            return {...og, "routes": [...og.routes, newRoute]}
        })
    }

    const handleNewCustomNote = () => {
        const empty = {id: nextId--, noteKey:"", noteValue:""}

        setData(og => {
            const info = JSON.parse(JSON.stringify(data.info));
            info.customNotes.push(empty);
            return {...og, info}
        })
    }

    const handleDelCustomNote = (index) => {
        setData(og => {
            const info = JSON.parse(JSON.stringify(data.info));
            info.customNotes.splice(index, 1)
            return {...og, info}
        })
    }

 if (!data) return(<div>Loading ...</div>);
    return(
        <main onClick={logState}>
            <div className="d-flex flex-row justify-content-between align-items-center">
             <input className="h1" name="name" defaultValue={data.info.name}
                onChange={e => handleChange(e.target.value, ["info"], "name")} 

                style={{ border: validationOn && !data.info.name? "red 3px dashed" : "" }}
                />

            <div>

            <Link to={`/admin-panel/lines/${lineId ? lineId : ""}`}
                className="btn btn-secondary mx-2"
            >Cancel</Link>

            <button className="btn btn-success"
            onClick={handleSubmit}
            >Save</button>
            </div>
            </div>


{/* Line Info Box */}
            <div className="row">
                <div className="col-12 col-md-6 order-md-2">
                <table>
                    <thead>
                    </thead>
                    <tbody>
                    {Object.keys(lineInfoLabel).map((infoKey, index) => (
                            <tr key={index}>
                              <td>{lineInfoLabel[infoKey]}</td>
                              <td> {infoKey === "routeType" ? 
                                <select className="form-select" 
                                style={{ border: validationOn && !data.info[infoKey]? "red 3px dashed" : "" }}
                                onChange={e => handleChange(e.target.value, ["info"], infoKey)}
                                defaultValue={data.info[infoKey]}>
                                    {data.info[infoKey] ?? <option value=""></option>}
                                {data.routeTypeOptions.map((option, optionIndex)=>
                                    <option value={option} key={optionIndex}>{option}</option>
                                )}
                              </select>
                              : infoKey === "enabledSeasonalYearlyRuleFilter" 
                              ? 
                              
                                [{label:"Yes", value:true}, {label:"No", value:false}].map(choice => (
                                    <div className="form-check d-inline-block" key={choice.label}>
                                    <label className="form-check-label ps-3">
                                    <input className="form-check-input" type="radio" checked={data.info.enabledSeasonalYearlyRuleFilter == choice.value}
                                        name="filterByRule" value={choice.value}
                                        onChange={(e) => handleChange(e.target.value === 'true', ["info"] , "enabledSeasonalYearlyRuleFilter", 0)} />
                                        {choice.label}
                                    </label>
                                </div>
                                ))
                              
                              : 
                              <input className="" name="value" defaultValue={data.info[infoKey]}
                              style={{ border: validationOn && (infoKey == "routeEnd" || infoKey == "routeStart" || infoKey == "routeType")&& !data.info[infoKey]? "red 3px dashed" : "", overflowX: "auto", whiteSpace: "nowrap" }}
                              onChange={e => handleChange(e.target.value, ["info"], infoKey)} />                              
                            }
                                </td>
                            </tr>                 
                        ))}
                        {
                            data.info.customNotes.length > 0 && 
                            data.info.customNotes.map((note, index) => (
                                <tr key={note.id}>
                                    <td>
                                        <input name="key" defaultValue={note.noteKey}
                                        style={{ border: validationOn && !data.info.customNotes[index].noteKey  ? "red 3px dashed" : ""}}
                                          onChange={e => handleChange(e.target.value, ["info", "customNotes", index], "noteKey")} />
                                    </td>
                                    <td>
                                    <input name="value" defaultValue={note.noteValue} 
                                    style={{ border: validationOn && !data.info.customNotes[index].noteValue  ? "red 3px dashed" : "", width: "calc(100% - 45px)"}}
                                          onChange={e => handleChange(e.target.value, ["info", "customNotes", index], "noteValue")} />  
                                    <FontAwesomeIcon className='btn px-1 ' icon={faTrashCan} onClick={()=>handleDelCustomNote(index)} />
                                    </td>
                                </tr>
                            ))
                        }

                            <tr>
                                <td colSpan={2}> 
                                <FontAwesomeIcon className='btn px-4 ' icon={faPlus} onClick={()=>handleNewCustomNote()} />
                                </td>
                            </tr>
                    </tbody>
                </table>

                </div>
                <div className="col-12 col-md-6 order-md-1 ">
                    <h2>Route variations:</h2>  
                    <ul className="list-group route-variations">
                        {/*  list-group-numbered */}
                        {data.routes.map((row, index)=>(
                              <li className={`list-group-item list-group-item-light
                                d-flex justify-content-around gap-1 p-0 align-items-center`} 
                                style={{background: 
                                    validationOn && (!row.routeNotes || row.stopsArr.some(x => !x.name)) ?
                                    "#ffabab" : ""}}
                                key={index}>
                                    <span className={`my-cursor-pointer rounded-3 p-2
                                        ${activeRouteIndexArr.includes(index) ? "active" : ""}`} 
                                        onClick={handleRoutesClick.bind(null,row.id)}
                                        style={{ padding:0, margin:0, flexGrow:5}}>
                                            {index+1}. {row.routeNotes }
                                            {/* if is used by schedules, give links */}
                                            { showScheduleLinks && data.routeUsage[index]?.map((x, index)=> <Link style={{fontSize: "0.7rem"}} key={index} className="btn btn-dark btn-sm py-0 px-1 mx-1" to={`/admin-panel/schedules/${lineId}/${x}`}>{x}</Link>   )}
                                    </span>
                                
                                <FontAwesomeIcon className='btn' icon={faCopy} onClick={()=>handleCopyRoute(index)} />

                                {data.routeUsage[index] && data.routeUsage[index].length !== 0 ? 
                                 <button className="btn"  onClick={()=>setShowScheduleLinks(og=> !og)}>{data.routeUsage[index].length} </button>
                                : 
                                <FontAwesomeIcon className='btn' icon={faTrashCan} onClick={()=>handleDeleteRoute(row, index)} />}
                              </li>
                        ))}
                        <li className="list-group-item list-group-item-light text-center m-0 p-0 border-0">
                            <FontAwesomeIcon className='btn px-4 ' icon={faPlus} onClick={()=>handleNewRoute()} />
                        </li>
                    </ul>
                </div>
            </div>
            <div className="row" style={{background: secondRowColor}}>
                <div className="col">
                    {activeRouteIndexArr.map(index =>   (
                        <BusStopTableEdit 
                        key={index} 
                        activeRoute={data.routes[index]} 
                        index={index} 
                        handleChange={handleChange}
                        handleAddStop={handleAddStop}
                        handleRemoveStop={handleRemoveStop}
                        handleStopCountChange={handleStopCountChange}
                        validationOn={validationOn}
                        />    
                    )   )

                        
                    }
                    
                </div>
            </div>

        </main>
    )
}