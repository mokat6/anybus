import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';


export default function TableSearch({handleSearchChange}){

    return(
            <div className="input-group mb-3">
                <span className="input-group-text" id="basic-addon1">
                    <FontAwesomeIcon icon={faMagnifyingGlass} />
                </span>
                <input 
                    className="form-control"
                    onChange={e => handleSearchChange(e.target.value)}
                />
            </div>
            
            
            
    )
}