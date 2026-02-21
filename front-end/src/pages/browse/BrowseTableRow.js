import { useState } from "react";

import { faChevronDown } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useLocation, useNavigate } from "react-router-dom"


export default function BrowseTableRow({ row, isEvenRow }) {
  const [isExpanded, setExpanded] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();

  const queryParams = new URLSearchParams(location.search);
  const destinationUrl = (path) => `${path}?${queryParams.toString()}`;


    const handleExpand = (e) =>{
        e.stopPropagation();
        setExpanded(og => !og);
    }

    const handleClick = (id) => {
        navigate(destinationUrl(`${id}`)); 
    }

  return (
    <>
      <tr
        onClick={()=>  handleClick(row.id)}
        className={`${isEvenRow ? "table-primary" : ""} my-cursor-pointer`}
      >
        <td><b>{row.name}</b></td>
        <td className="col-md-4 col-lg-2" >{row.goesTo}</td>
        <td onClick={(e) => handleExpand(e)}
        className="my-hover-effect text-center d-md-none">
            <FontAwesomeIcon icon={faChevronDown} className={isExpanded?"expand-up":""} /></td>
        <td className="my-ellipsis d-none d-md-table-cell">{row.mainStops.map(stop => stop.name).join(", ")}</td>
      </tr>
      {isExpanded && 
        <tr>
          <td className="pb-5 table-secondary" colSpan={3}>
            {row.mainStops?.map(obj=>obj.name).join(", ")} 
            {row.extraStops && 
            <p className="mt-3">
              <span className="text-secondary">*Extra: </span> 
              {row.extraStops?.map(obj=>obj.name).join(", ")} 
            </p> }
          </td>
        </tr>
}
    </>
  );
}
