import { Link, useLocation, useNavigate  } from 'react-router-dom';


const color = {
  "City Bus": "#F3A6AE",
  "Regional Bus": "#AEF3A6",
  "Nationial Bus": "#A6AEF3"
}

function ScheduleItem({item}) {
  const location = useLocation();

  const queryParams = new URLSearchParams(location.search);
  const destinationUrl = (path) => `${path}?${queryParams.toString()}`;
  
  
  
  return (
    <Link title={item.lineName} style={{background:color[item.routeType]}}
      className={`schedule-item text-decoration-none text-dark rounded-3 p-1 mx-1 d-inline-block ${item.tooLate ? 'tooLate' : ''}`} 
      to={destinationUrl(`/browse/${item.lineId}/${item.schedId}/${item.tripId}`)}>
           <span>{item.timeDepart.slice(0, 5)}</span>        <span>{item.name}</span>
  
  </Link>
  );
}

export default ScheduleItem;
