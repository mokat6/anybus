import { NavLink, Link, useMatch, useNavigate } from "react-router-dom"
import AuthService from "services/auth.service";

export default function SideBar(){
  // Use useMatch hook to check if the current route matches exactly
  const isExactAdminPanel = useMatch('/admin-panel');
  const navigate = useNavigate();

  const handleLogOut = () => {
    AuthService.logout();
    navigate("/");        
    window.location.reload();
   }

    return(

<ul className="nav flex-column">
  <li className="nav-item">
    <NavLink className="nav-link" to="/admin-panel" end>Admin home</NavLink>
  </li>
  <li className="nav-item">
  <NavLink className="nav-link" to="/admin-panel/holidays">Configure holidays</NavLink>
  </li>
  <li className="nav-item">
  <NavLink className="nav-link" to="/admin-panel/bus-stops">Bus stops</NavLink>
  </li>
  <li className="nav-item">
  <NavLink className="nav-link" to="/admin-panel/lines">Bus lines</NavLink>
  </li>
  <li className="nav-item">
  <NavLink className="nav-link" to="/admin-panel/schedules">Schedules</NavLink>
  </li>
  <li className="nav-item">
  <NavLink className="nav-link" to="/admin-panel/yearly-rules">Yearly rules</NavLink>
  </li>
  <li className="nav-item">
    <Link onClick={handleLogOut} className="nav-link"> Log out</Link>
  </li>
</ul>

    )
}