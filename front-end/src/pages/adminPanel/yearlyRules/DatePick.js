import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css'; // Import CSS

export default function DatePick({dateRange, handleChange, index}){
 //2026 - just a random fixed year, must be not a leap year
 
    const [startDate, endDate] = (() => {
        let start = null, end = null;

        const startMonthFormatted = String(dateRange.startMonth).padStart(2, '0');
        const startDayFormatted = String(dateRange.startDay).padStart(2, '0');
        const endMonthFormatted = String(dateRange.endMonth).padStart(2, '0');
        const endDayFormatted = String(dateRange.endDay).padStart(2, '0');

        if (dateRange.startMonth && dateRange.startDay) 
            start = new Date(`2026-${startMonthFormatted}-${startDayFormatted}`);
        
        if (dateRange.endMonth && dateRange.endDay)             
            end = new Date(`2026-${endMonthFormatted}-${endMonthFormatted}`);
        
            return [ start, end]
    })();


    const handleLocalChange = (update) => {
        console.log("update", update);
        const dateRangeObj = {...dateRange}
        dateRangeObj.startMonth = update[0]?.getMonth() + 1;
        dateRangeObj.startDay   = update[0]?.getDate();
        dateRangeObj.endMonth   = update[1]?.getMonth() + 1;
        dateRangeObj.endDay     = update[1]?.getDate();
        
        handleChange(dateRangeObj, ["timePeriods"], index, 0)

        console.log("this one update",update);

    }

    return(
        <DatePicker 
                            showIcon
                            startDate={startDate}
                            endDate={endDate}

                            onChange={(update) => handleLocalChange(update)}
                            withPortal        

                            dateFormat="MMM/dd"

                            dateFormatCalendar="MMMM"
                            placeholderText="Select Date"
                            className="form-control"
                            monthsShown={3}
                            selectsRange={true}
                            minDate={new Date("2026-01-01")}
                            maxDate={new Date("2026-12-31")}
                            portalId="root-portal"

                     />
     )
}