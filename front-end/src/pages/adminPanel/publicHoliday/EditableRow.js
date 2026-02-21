import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';

  import DatePicker from 'react-datepicker';
  import 'react-datepicker/dist/react-datepicker.css'; // Import CSS

  import { useState } from "react";


export default function EditableRow({editTRowData, handleEditFormChange, handleEditDatePickerChange, handleCancelClick}){

    return (
        <tr>
            <td>
                <input name="name" placeholder="Enter new holiday..." onChange={handleEditFormChange}
                    className="form-control" value={editTRowData.name} required  
                    />            </td>
            <td>
            <DatePicker 
                    showIcon
                    // selected={formData.calendarDate}
                    selected={editTRowData.calendarDate}
                    // onChange={(date) => handleDatePickerChange(date)}
                    onChange={(date) => handleEditDatePickerChange(date)}
                    dateFormat="MMM-dd"
                    icon={<FontAwesomeIcon icon={faCalendar} />}
                    dateFormatCalendar="MMMM"
                    placeholderText="Select Date"
                    className="form-control"
                     />
            </td>
            <td>
            <span className="table-action-td">
                <button className="btn btn-success btn-sm" type="submit">save</button>
                <button className="btn btn-secondary btn-sm" type="button" onClick={handleCancelClick}>cancel</button>
            </span>
            </td>
        </tr>
    )
}