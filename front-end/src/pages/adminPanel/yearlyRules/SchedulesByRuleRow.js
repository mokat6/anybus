import { faArrowLeft,  faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export default function SchedulesByRuleRow ({schedule}) {

    const generateName = (tripDir, index) => {
        if (!tripDir.time || !tripDir.dir[0]) return "Dir & Time"
        let name;
        if (tripDir.dir === "Out bound") name = <FontAwesomeIcon icon={faArrowLeft} style={{width: "16px",transform: "scaleX(-1)"}} />
        else if (tripDir.dir === "City bound") name = <FontAwesomeIcon icon={faArrowLeft} style={{ width: "16px"}} />

        name = <span style={{width:'75px'}} key={index}>{name} {tripDir.time}</span>; 

        return name;
    }

        const daysOfWeek = () => {
            const week = [["MONDAY", "M"], ["TUESDAY", "T"], ["WEDNESDAY", "W"], ["THURSDAY", "T"], ["FRIDAY", "F"], ["SATURDAY", "S"], ["SUNDAY", "S"]];
            let days = <div className='d-inline-block'> 
            {
                week.map((day, i) => (
                    <span style={{fontSize:"0.8rem"}} key={i}
                    className={`rounded-1  ${schedule.runsOnWeekly.includes(day[0])? "bg-dark" : "my-schedule-picker-faded"} text-white p-1`}>{day[1]}</span>        
                    ))
            }            
 
            
            </div>
            return days;
        }

    return(
        <span className='d-inline-flex gap-3'>
                {daysOfWeek()} 
                {schedule.tripsFirstEntryAndDir.map((tripDir, index) => (
                        generateName(tripDir, index)
                ))}
        </span>
    )
}