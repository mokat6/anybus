import TimeInput from "./TimeInput";
import { faTrashCan } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export default function Timetable({schedule, selectedSchedule, className, routes, handleRouteChange, handleReverseStops, handleChange, staticData, handleDeleteTrip, validationOn}){
    
    const timeConvert = (string) => {
        if (!string) return "";

        const [hours, mins] = string.split(":")
        return `${hours}:${mins}`;
    }


    const get1RouteFromArray = (trip) => {
        let theRoute;
        if (!trip.routeId) 
            theRoute = routes[0];
        else
            theRoute = routes.find(route => route.id === trip.routeId);


        // theRoute.stopsArr = theRoute.stopsArr.slice();
        let busStops = [...theRoute.stopsArr]
        trip.routeDirReversed && busStops.reverse();
        let myArr = busStops.map((stop, index)=>  ({stop:stop.name, time:timeConvert(trip.timeList[index])}))

        return myArr;
    }

    // if (!routes || !schedule) return <div>loading</div>;
    return (
        // <div  className={className}>
        <>
        { //this trip.id is GOOD unique
            schedule.trips.map((trip, index)=> (
                <table className="caption-top  d-inline-block" key={trip.id}>
{/* -----------CAPTION-------------------------------------- */}
                    <caption>  
                        {/* direction select */}
                        <div>
                            <select className="form-select" aria-label="Default select example" key={index}
                                    onChange={e => handleChange(e.target.value, ["trips", index, ], "boundFor", 0)}
                                    value={trip.boundFor}>

                                    {
                                        staticData.boundForOptions.map((boundForEnum, index) => (//this key is unique, GOOD
                                            <option key={index} value={boundForEnum}>{boundForEnum}</option>
                                        ))
                                    }
                            </select>
                        </div>
                        {/* route select */}
                        <div>
                            <select className="form-select" aria-label="Default select example" key={index}
                            value={trip.routeId}
                            onChange={(e)=> handleRouteChange(Number(e.target.value), index)}>
                                    {
                                        routes.map(route => (//this key is unique, GOOD
                                            <option key={route.id} value={route.id}>{route.routeNotes}</option>
                                        ))
                                    }
                            </select>
                        </div>
                        {/* reverse switch */}
                        <div className="form-check form-switch">
                            <label className="form-check-label">
                                <input className="form-check-input" type="checkbox" role="switch"  
                                onChange={(e)=>handleReverseStops(e.target.checked, index)} 
                                checked={trip.routeDirReversed}
                                />
                            Reverse</label>
                        </div>
                    </caption>
{/* -----------ENDS CAPTION-------------------------------------- */}
                    <thead>
                        <tr>
                            <th>Name</th><th>Time</th>
                        </tr>
                    </thead>
                    <tbody>
{/* --------- 1 table body ----------------------------- */}
                        {
                            get1RouteFromArray(trip) 
                            // get1RouteFromArray(trip.routeId, trip.routeDirReversed)
                            // routes.find(route => route.id === trip.routeId)
                            .map((row, i) => ( 
                                <tr key={i}>
                                <td>{row.stop} </td>
                                <td> 
                                    {
                                        <TimeInput defaultValue={row.time} 
                                        handleChange={handleChange} path={["trips", index, "timeList"]} inputIndex={i}
                                        className={validationOn && !row.time? "my-valid-border" : ""}
                                        ></TimeInput>
                                    }
                                </td>
                            </tr>
                            ))
                        }
                    </tbody>
                    <tfoot>
                        <tr>
                            <td  className="pt-4 text-center" colSpan="2">
                            <FontAwesomeIcon className='btn btn-sm' icon={faTrashCan}  onClick={()=>handleDeleteTrip(trip)}  />
                            </td>
                        </tr>
                    </tfoot>
                </table>
            ))
        }
        </>
    )
}