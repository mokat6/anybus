import { faTrashCan, faPencil } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import { Link } from 'react-router-dom';


export default function ReadOnlyRow({ row, handleEditClick, handleDeleteClick, usedInLines }) {
  const [showingUsage, setShowingUsage] = useState(false);

  return (
    <>
    <tr>
      <td>{row.name}</td>
      <td>{row.coords}</td>
      <td>
        <div className="form-check form-switch">
          <input type="checkbox" checked={row.defaultOption} className='form-check-input secondary' 
          readOnly />
        </div>
      </td>
      <td>
        <div className="d-flex justify-content-around">
        <FontAwesomeIcon className='btn btn-sm' icon={faPencil} onClick={()=>handleEditClick(row)} />

        <button type="button" className="btn btn-sm py-0" onClick={()=> setShowingUsage(og => !og)}>
          <span className={`badge text-bg-${usedInLines.length > 0 ? "primary" : "danger"}`}>{usedInLines.length}</span>
        </button>

        <button className='btn btn-sm py-0' disabled={usedInLines.length > 0} onClick={()=>handleDeleteClick(row)}><FontAwesomeIcon icon={faTrashCan} /></button>

        </div>
      </td>
    </tr>

    {
      showingUsage && 
      <tr>
        <td className='bg-secondary text-light' colSpan={4}>
          {
            usedInLines.map((line, index) => (
              <Link to={`/admin-panel/lines/${line.id}`} key={index}
                className="text-decoration-none text-light  m-2">
                <span key={line.id} className='bg-dark rounded p-1' style={{fontSize: "0.7em"}}>{line.name}</span>
              </Link>
              
            ))  
          }
        </td>      
      </tr>
    }
    
    </>
  );
}
