import { NavLink, Link, useLocation  } from 'react-router-dom';

export default function Navbar({admin}) {

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const destinationUrl = (path) => `${path}?${queryParams.toString()}`;


  return (

<nav className="navbar navbar-expand" >
  <div className="container-fluid">
    <span className="navbar-brand">🚍</span>
    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse" id="navbarNav">
      <ul className="navbar-nav">
        
        <li className="nav-item">
          <NavLink className="nav-link text-light" to={destinationUrl("/")} >Timetable</NavLink>
        </li>
        
        <li className="nav-item">
          <NavLink className="nav-link text-light" to={destinationUrl("/browse")}>Browse by line</NavLink>
        </li>


        </ul>
        <ul className="navbar-nav">
        {admin && <>
              <li className="nav-item">
                  <NavLink className="nav-link text-light" to="/admin-panel">Admin board</NavLink>
              </li>                
                
                </>
              }
          {!admin && 
               <li className="nav-item">
                  <NavLink className="nav-link text-light" to="/admin-access">Admin board</NavLink>
              </li>  }
          </ul>
        </div>


    
      

  </div>
</nav>

  );
};

