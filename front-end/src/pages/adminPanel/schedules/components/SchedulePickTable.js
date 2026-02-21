import { faArrowLeft,  faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export default function SchedulePickTable({data, className, handleNewSchedule, selectedSchedule, handleClickOpen,
        validationOn}) {
    
    
    
    const generateName = (trip) => {
        if (!trip.boundFor || !trip.timeList[0]) return "Dir & Time"
        let name;
        if (trip.boundFor === "Out bound") name = <FontAwesomeIcon icon={faArrowLeft} style={{width: "16px",transform: "scaleX(-1)"}} />
        else if (trip.boundFor === "City bound") name = <FontAwesomeIcon icon={faArrowLeft} style={{ width: "16px"}} />

        const [hours, mins] = trip?.timeList[0].split(":")
        name = <span key={trip.id}>{name} {`${hours}:${mins}`}</span>; 

        return name;
    }

        const daysOfWeek = (row) => {
            const week = [["MONDAY", "M"], ["TUESDAY", "T"], ["WEDNESDAY", "W"], ["THURSDAY", "T"], ["FRIDAY", "F"], ["SATURDAY", "S"], ["SUNDAY", "S"]];
            let days = <div className='d-flex justify-content-between'> 
            {
                week.map((day, i) => (
                    <span style={{fontSize:"0.8rem"}} key={i}
                    className={`rounded-1  ${row.runsOnWeekly.includes(day[0])? "bg-dark" : "my-schedule-picker-faded"} text-white p-1`}>{day[1]}</span>        
                    ))
            }            
 
            
            </div>
            return days;
        }


    return (

        <div className={`d-flex flex-wrap column-gap-3 align-items-start ${className} `} style={{maxWidth:"780px"}}> 
                    {
                        data.length == 0 
                        ? 
                        <div>This line has no schedules yet, create new...</div>
                        :
                        data.map((row, index) => (
                            <div className={`${index === selectedSchedule ? "bg-primary my-active" : ""} p-1`} 
                            style={{border: 
                                validationOn && (row.runsOnWeekly.length === 0 || 
                                    !row.runsOnYearlyId || row.trips.length < 1 || row.trips.length > 2 || (row.trips.length === 1 && row.trips[0].boundFor != "One way") ||
/* this one line checks 1 thing */   row.trips.length === 2 && !(row.trips.map(trip => trip.boundFor).includes("Out bound") && row.trips.map(trip => trip.boundFor).includes("City bound")) ||     
                                    row.trips.some(trip => trip.timeList.some(time=> !time))) ?
                                "2px dashed red" : ""}}
                                key={index} onClick={()=>handleClickOpen(index)}>
                            <div className="my-cursor-pointer my-active-scale-down d-flex gap-1">
                                {daysOfWeek(row)}{row.trips.map(t=>generateName(t))}
                            </div>
                            </div>
                            ))
                    }
                        <div>
                        <button className="btn btn-light" onClick={handleNewSchedule}>
                    <FontAwesomeIcon icon={faPlus} /></button>
                     
                        </div>
        </div>  
    )
}
