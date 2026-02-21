import ReadOnlyRow from "./ReadOnlyRow";
import EditableRow from "./EditableRow";
import { apiGetAllHolidays, apiPostHolidaysSave, apiHolidayDel } from "services/user.service.js"

//
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';

//
//https://reactdatepicker.com/
  import DatePicker from 'react-datepicker';
  import 'react-datepicker/dist/react-datepicker.css'; // Import CSS
  import { useState, useEffect } from "react";

  
export default function PublicHolidayManager(){
    const [formData, setFormData] = useState({ name: '', calendarDate: "" });
    const [tableData, setTableData] = useState([]);
    const [editHolidayId, setEditHolidayId] = useState(null);
    const [editTRowData, setEditTRowData] = useState({name: '', calendarDate: ""})
    const [errorMsg, setErrorMsg] = useState();


    useEffect(() => {
        apiGetAllHolidays().then(
          response => setTableData(response.data), 
          error => setErrorMsg(error?.response?.data?.message || error.message || error.toString())
        )
      }, [])
    

    const handleAddFormChange = (event) => {
        const { name, value } = event.target;
        setFormData(values => ({...values, [name]: value}))
        console.log(formData);
    }

    const handleDatePickerChange = (newDate) => {

        setFormData(values => ({...values, 
            ["month"]:newDate.getMonth() + 1,
            ["day"]:newDate.getDate(),
            ["calendarDate"]: newDate
        }))

        console.log(formData);
    }

    const handleEditFormChange = (event) =>{
        const {name, value} = event.target;
        console.log(event)
        setEditTRowData({...editTRowData, [name]:value})
    }
    const handleEditDatePickerChange = (newDate) => {
        setEditTRowData( values =>({...values,
            ["month"]:newDate.getMonth() + 1,
            ["day"]:newDate.getDate(),
            ["calendarDate"]: newDate
        }))
    }


    const handleAddFormSubmit = async (event) => {
        event.preventDefault();

        console.log(formData);        
        const newHoliday = {
            name: formData.name,
            month: formData.month,
            day: formData.day,
        }   
        

        apiPostHolidaysSave(newHoliday)
        .then(response => {
          console.log('Entry added successfully:', response.data);
          setTableData(oldVals => ([...oldVals, response.data]));
          setFormData({ name: '', calendarDate: '' });
        })
        .catch(error => console.error('Error adding entry:', error.message));
    }
    const handleEditFormSubmit = (event) => {
        event.preventDefault();
        console.log(editTRowData);
        const editedHoliday = {
            name: editTRowData.name,
            month: editTRowData.month,
            day: editTRowData.day,
            id: editHolidayId
        }

        

        apiPostHolidaysSave(editedHoliday)
            .then(response => {
                console.log("Entry updated successfully:", response.data);
                setTableData(ogArray => {
                    const index = ogArray.findIndex(x=> x.id === editHolidayId)
                    const newArray = [...ogArray]
                    newArray[index] = response.data
                    return newArray;
                })})
                .catch(error => console.error('Error updating entry: ', error.message))
            
                setEditHolidayId(null);
    }

    const handleEditClick = (event, holiday)=> {
        event.preventDefault();
        console.log(holiday);
        setEditHolidayId(holiday.id);

        const rowValues = {
            name: holiday.name,
            calendarDate: new Date(new Date().getFullYear(), holiday.month - 1, holiday.day   ),
            month: holiday.month,
            day: holiday.day

        }
        setEditTRowData(rowValues)
    }

    const handleCancelClick = () => {
        setEditHolidayId(null);
    }

    const handleDeleteClick = (delHoliday) => {
        setTableData(og=> og.filter(x=> x !== delHoliday))
        apiHolidayDel(delHoliday.id)
    }

    if (errorMsg) return <p className="alert alert-danger">{errorMsg}</p>;
    return (
        <>
            <h1>Manage public holidays</h1>
            <form className="row g-3" onSubmit={handleAddFormSubmit} autoComplete="off">
                <div className="col-auto">
                    <input name="name" placeholder="Enter new holiday..." onChange={handleAddFormChange}
                    className="form-control" value={formData.name} />
                </div>
                <div className="col-auto">
                    <DatePicker 
                    showIcon
                    selected={formData.calendarDate}
                    onChange={(date) => handleDatePickerChange(date)}
                    dateFormat="MMM-dd"
                    icon={<FontAwesomeIcon icon={faCalendar} />}
                    dateFormatCalendar="MMMM"
                    placeholderText="Select Date"
                    className="form-control"
                     />
                </div>
                <div className="col-auto">
                    <input type='submit' className="btn btn-primary" />
                </div>
            </form>
  
        <form onSubmit={handleEditFormSubmit} autoComplete="off">
        <table className='table table-striped '>
            <thead>
                <tr>
                    <th>Holiday</th><th style={{width:"150px"}}>Date</th><th style={{width:"140px"}}>Actions</th>
                </tr>
            </thead>
            <tbody>
                {tableData.map(holiday=>(

                editHolidayId === holiday.id ? 
                    <EditableRow editTRowData={editTRowData} handleEditFormChange={handleEditFormChange} key={holiday.id}
                    handleEditDatePickerChange={handleEditDatePickerChange} handleCancelClick={handleCancelClick} /> : 
                    <ReadOnlyRow holiday={holiday} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} key={holiday.id} />

                 ))}
            </tbody>
            </table>
            </form>


            

        </>
    )
}