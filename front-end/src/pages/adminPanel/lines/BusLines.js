import { useEffect, useState } from "react"
import LineTableRow from "./LineTableRow"
import { apiGetLinesPreview } from "services/user.service.js"
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from "react-router-dom";

export default function BusLine(){
    const [tableData, setTableData] = useState([])

    useEffect(() => {
        apiGetLinesPreview()
        .then(response => setTableData(response.data))
        .catch(error => console.log("Error fetching data @BusLine.js ", error))
    }, [])

    const handleAddLine = () => {
        console.log(tableData);
    }

    return (
        <table className="table ">
            <thead>
                <tr>
                    <th>Name</th><th>From</th><th>To</th><th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {tableData.map((row, i)=>(
                    <LineTableRow row={row} key={row.id} isEvenRow={i%2 === 0} />
                ))}
                <tr><td colSpan={4} className="text-center m-0 p-0 border-0"> 
                    

                    <Link to={`/admin-panel/lines/new`}
                          
                    ><FontAwesomeIcon className='btn px-4 ' icon={faPlus} onClick={handleAddLine} /></Link>


                    </td></tr>
              </tbody>
        </table>
    )
}