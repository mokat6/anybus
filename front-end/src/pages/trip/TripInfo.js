import { lineInfoLabel } from "utils/myUtils";

const daysOfWeek = (runsOnWeekly) => {
    const week = [["MONDAY", "M"], ["TUESDAY", "T"], ["WEDNESDAY", "W"], ["THURSDAY", "T"], ["FRIDAY", "F"], ["SATURDAY", "S"], ["SUNDAY", "S"]];
    let days = <div className='d-flex justify-content-between'> 
    {
        week.map((day, i) => (
            <span style={{fontSize:"0.8rem"}} key={i}
            className={`rounded-1  ${runsOnWeekly.includes(day[0])? "bg-dark" : "my-schedule-picker-faded"} text-white p-1`}>{day[1]}</span>        
            ))
    }            
    </div>
    return days;
}

export default function TripInfo({lineInfo, data}){
    // lineInfoLabel


    return (
        <div>
            <table className="table table-light" >
                <thead></thead>
                <tbody>
                    <tr><td>Line</td><td><span className="position-relative">{lineInfo.name} <span className="badge text-bg-primary">More schedules</span></span></td></tr>
                    <tr><td>Operator</td><td>{lineInfo.operator}</td></tr>
                    <tr><td>Anyksciai AS platform</td><td>{lineInfo.anykStationPlatform}</td></tr>
                </tbody>
            </table>

            <table className="table ">
                <thead>
                    <tr>
                        <th colSpan={2} className="text-center">Time constraints</th>
                    </tr>
                </thead>
                <tbody>
                    <tr><td>Runs on public holidays</td><td>{data.runsOnPublicHolidays ? "Yes" : "No"}</td></tr>
                    <tr><td>Days of week</td><td>{daysOfWeek(data.runsOnWeekly)}</td></tr>
                    {
                        data.runsOnYearly.periodName != "Apskritus metus" &&
                        <tr><td>Only at</td><td>{data.runsOnYearly.periodName}</td></tr>
                    }
                    
                </tbody>
            </table>


            {/* <p>{lineInfo.lineName}</p>
            <p>{lineInfo.routeStart}</p>
            <p>{lineInfo.routeEnd}</p>
            <p>{lineInfo.operator}</p>
            <p>{lineInfo.anykStationPlatform}</p>
            <p>{lineInfo.price}</p>
            <p>{lineInfo.routeType}</p>
            <p>{lineInfo.timeConstraintsDescription}</p> */}
        </div>
    );

}

