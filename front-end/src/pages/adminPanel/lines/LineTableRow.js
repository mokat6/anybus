import { useState } from "react";

import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useNavigate } from "react-router-dom"


export default function LineTableRow({ row, isEvenRow }) {
  const [isExpanded, setExpanded] = useState(false);
  const navigate = useNavigate();

    const handleExpand = (e) =>{
        e.stopPropagation();
        setExpanded(og => !og);
    }

    const handleClick = (id) => {
        navigate(`${id}`)
    }

  return (
    <>
      <tr
        onClick={()=>  handleClick(row.id)}
        className={`${isEvenRow ? "table-primary" : ""} my-cursor-pointer`}
      >
        <td><b>{row.name}</b></td>
        <td>{row.routeStart}</td>
        <td>{row.routeEnd}</td>
        <td onClick={(e) => handleExpand(e)}
        className="my-hover-effect text-center">
            <FontAwesomeIcon icon={faChevronDown} className={isExpanded?"expand-up":""} /></td>
      </tr>
      {isExpanded ? (
        <tr>
          <td className="pb-5 table-secondary" colSpan={5}>
            {row.mainStops?.join(", ")} 
            {row.extraStops && 
            <p className="mt-3">
              <span className="text-secondary">*Extra: </span> 
              {row.extraStops?.join(", ")} 
            </p> }
          </td>
        </tr>
      ) : (
        ""
      )}
    </>
  );
}
