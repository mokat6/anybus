import { faTrashCan, faPencil } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export default function ReadOnlyRow({ holiday, handleEditClick, handleDeleteClick }) {
  return (
    <tr>
      <td>{holiday.name}</td>
      <td>{`${String(holiday.month).padStart(2, "0")}\u2011${String(
        holiday.day
      ).padStart(2, "0")}`}</td>
      <td>
        {/* <button type="button" onClick={event=>handleEditClick(event, holiday)}>Edit</button>
        <button type="button" onClick={event=>handleDeleteClick(holiday)}>Delete</button> */}
        <span className="table-action-td">
        <FontAwesomeIcon className='btn btn-sm' icon={faPencil} onClick={event=>handleEditClick(event, holiday)} />
        <FontAwesomeIcon className='btn btn-sm' icon={faTrashCan} onClick={event=>handleDeleteClick(holiday)} />
        </span>
      </td>
    </tr>
  );
}
