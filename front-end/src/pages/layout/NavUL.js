import { NavLink  } from "react-router-dom";

export default function NavUL (props){

return(
    <ul className={props.ulClass}>
            <li className={props.liClass}>
              <NavLink className={props.aClass}  to="/"
              onClick={(event) => props.clickFunc(event.target.innerText)}>Today</NavLink>
            </li>
            <li className={props.liClass}>
              <NavLink className={props.aClass}  to="/tomorrow"
              onClick={(event) => props.clickFunc(event.target.innerText)}>Tomorrow</NavLink>
            </li>
            <li className={props.liClass}>
              <NavLink className={props.aClass}  to="/2dayslater"
              onClick={(event) => props.clickFunc(event.target.innerText)}>Saturday</NavLink>
            </li>
            <li className={props.liClass}>
              <NavLink className={props.aClass}  to="/3dayslater"
              onClick={(event) => props.clickFunc(event.target.innerText)}>Sunday</NavLink>
            </li>
          </ul>
)

}