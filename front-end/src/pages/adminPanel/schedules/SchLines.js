import { useEffect, useState } from "react"
import LineTableRow from "../lines/LineTableRow";
import { apiGetLinesPreview } from "services/user.service.js"


export default function SchLines(){
    const [tableData, setTableData] = useState([])

    useEffect(() => {
        apiGetLinesPreview()
        .then(response => setTableData(response.data))
        .catch(error => console.log("Error fetching data @BusLine.js ", error))
    }, [])



    return (
        <table className="table">
            <thead>
                <tr>
                    <th>Name</th><th>From</th><th>To</th><th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {tableData.map((row, i)=>(
                    <LineTableRow row={row} key={row.id} isEvenRow={i%2 === 0} />
                ))}

              </tbody>
        </table>
    )

}




