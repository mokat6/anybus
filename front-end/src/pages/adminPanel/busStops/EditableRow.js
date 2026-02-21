
export default function EditableRow({editTRowData, handleEditFormChange, handleCancelClick}){
console.log(editTRowData);
    return (
        <tr>
            <td>
                <input name="name" placeholder="Enter new bus stop..." onChange={handleEditFormChange}
                    className="form-control" value={editTRowData.name} required  
                    />            </td>
            <td>
            <input name="coords" placeholder="Location..." onChange={handleEditFormChange}
                    className="form-control" value={editTRowData.coords ?? ""} autoComplete="off" />
            </td>
            <td>
                <div className="form-check form-switch">
                    <input type="checkbox" name="defaultOption"  onChange={handleEditFormChange}
                        className="form-check-input" checked={editTRowData.defaultOption} autoComplete="off" />
                </div>
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