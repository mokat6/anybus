
export default function Constraints({schedule, handleChange, yearlyOptions, data, validationOn}){

    const weekDays = [
        { value: "MONDAY", label: "Monday" },
        { value: "TUESDAY", label: "Tuesday" },
        { value: "WEDNESDAY", label: "Wednesday" },
        { value: "THURSDAY", label: "Thursday" },
        { value: "FRIDAY", label: "Friday" },
        { value: "SATURDAY", label: "Saturday" },
        { value: "SUNDAY", label: "Sunday" }
    ];

const handleChangeWeekDays = (e) => {
    const {value, checked} = e.target;
    console.log("value is___" , value)
    console.log("checked is___" , checked)

    let runsOnWeekly = [... schedule.runsOnWeekly];
    if (checked) 
        runsOnWeekly.push(value);
    else
        runsOnWeekly = runsOnWeekly.filter(day => day !== value)
    // schedule.runsOnWeekly.includes(day.value)

    handleChange(runsOnWeekly, [] , "runsOnWeekly", 0)
}


    return(
        <div className="mt-4 border border-grey bg-secondary p-3" style={{userSelect: "none"}}>
            <div>
                <p className="fw-bold">Runs on public holidays:</p>
                
                <div className="form-check d-inline-block">
                    <label className="form-check-label ps-3">
                    <input className="form-check-input" type="radio" checked={schedule.runsOnPublicHolidays == false || schedule.runsOnPublicHolidays == "false"}
                        name="runsOnHolidays" value={"false"}
                        onChange={(e) => handleChange(e.target.value, [] , "runsOnPublicHolidays", 0)} />
                        No
                    </label>
                </div>
                
                <div className="form-check d-inline-block">
                    <label className="form-check-label ps-3">
                    <input className="form-check-input" type="radio" checked={schedule.runsOnPublicHolidays == true || schedule.runsOnPublicHolidays == "true"}
                        name="runsOnHolidays" value={"true"}
                        onChange={(e) => handleChange(e.target.value, [] , "runsOnPublicHolidays", 0)} />
                        Yes
                    </label>
                </div>
            </div>

            {/* ------------------WEEKDAYS------------------ */}
            <div className="mt-4">
            <p className="fw-bold">Days of week:</p>
            <div className={validationOn && schedule.runsOnWeekly.length == 0? "my-valid-border" : ""}>
            {weekDays.map((day, index) => (
                <div className="form-check" key={index}>
                    <label className="form-check-label ps-3">
                    <input
                        className="form-check-input"
                        name="weekdays"
                        type="checkbox"
                        value={day.value}
                        checked={schedule.runsOnWeekly.includes(day.value)}
                        onChange={(e) => handleChangeWeekDays(e)}
                    />
                        {day.label}
                    </label>
                </div>
                 ))}
             </div>
            </div>

            {/* --------------- YEARLY----------------- */}
            <div className="mt-4" style={{maxWidth: "200px"}}>
                <p className="fw-bold">Yearly constraints: </p>
                <div>
            <select className={`form-select ${validationOn && !schedule.runsOnYearlyId? "my-valid-border" : ""}`}
            value={schedule.runsOnYearlyId ?? ""}
            onChange={(e) => handleChange(e.target.value, [] , "runsOnYearlyId", 0)} >
            {schedule.runsOnYearlyId ?? <option value="" key={-123}></option>}
            {
                yearlyOptions.map((option, index) => (
                    <option value={option.id} className="" key={index}>
                        {option.periodName}
                        </option>
                ))
            }
            </select>
            </div>
            </div>
            {/* describe in human langauge */}
            <div className={`mt-4`}>
                <p className="fw-bold">In human language:</p>
                <textarea key={schedule.id} placeholder="Describe..." 
                className={`form-control`} 
                 defaultValue={schedule.timeConstraintsDescription}  
                 onChange={(e) => handleChange(e.target.value, [] , "timeConstraintsDescription")}  />
                        
            </div>

        </div> /* closes returning element */
      
    )
}