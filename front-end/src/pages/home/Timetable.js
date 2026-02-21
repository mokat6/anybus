import ScheduleItem from "./ScheduleItem";
import { useState, useEffect } from "react";

import { Link, useParams } from "react-router-dom";
import { dateRegex, dateToString, validateInteger } from "utils/myUtils";

import { useLocation } from 'react-router-dom';
import {apiGetHomeScheduleItems} from 'services/user.service.js';

import { useSearchParams } from 'react-router-dom';


export default function Timetable() {
  const [data, setData] = useState([]);
  const [errorMsg, setErrorMsg] = useState();
  
  const [bstopFrom, setBstopFrom] = useState();
  const [bstopTo, setBstopTo] = useState();
  const [queryDir, setQueryDir] = useState();
  const [queryDate, setQueryDate] = useState();

  const [searchParams] = useSearchParams();
  const [noSchedulesFound, setNoSchedulesFound] = useState(false);


  useEffect(()=>{
    setBstopFrom( validateInteger( searchParams.get('from')) || 1 );
    setBstopTo( validateInteger(searchParams.get('to')) );
    setQueryDir( searchParams.get('dir') === "City bound" ? "City bound" : "Out bound");

    const qdate = searchParams.get('date');

    if (dateRegex.test(qdate))
      setQueryDate(qdate)
    else 
      setQueryDate(dateToString(new Date()))
    // setQueryDate( searchParams.get('date') ?? dateToString(new Date()) );

  },[searchParams])
    

  const location = useLocation();


  

useEffect(() => {
  if (!bstopFrom || !queryDir || !queryDate) return

  logState()

  apiGetHomeScheduleItems(queryDate, queryDir, bstopFrom, bstopTo)
    .then(
      response => {
        setData(response.data)
        if (response.data.length === 0 ) setNoSchedulesFound(true);
        else (setNoSchedulesFound(false))
      }, 
      error => setErrorMsg(error?.response?.data?.message || error.message || error.toString())
      )
}, [queryDate, bstopFrom, queryDir, bstopTo]);


const logState = () => {
  console.log("data > ", data);
  console.log("bstopFrom > ", bstopFrom);
  console.log("bstopTo > ", bstopTo);
  console.log("queryDir > ", queryDir);
  console.log("queryDate > ", queryDate);
}

  let start = 6 , end = 23;
  //if earliest scheduled before 6am, then adjust "start"
  let earliestScheduled = Math.min(   data.map(item => parseInt(item.timeDepart.slice(0,2)))  );
  if (earliestScheduled && earliestScheduled < start) start = earliestScheduled;
  let latestScheduled = Math.max(   data.map(item => parseInt(item.timeDepart.slice(0,2)))  );
  if (latestScheduled && latestScheduled > end) end = latestScheduled;

  let range = new Array(end - start).fill(0).map((val, ind) => start + ind);

  range = range.map((hour) => {
    return {
      hour: hour,
      items: data.filter((trip) => hour == parseInt(trip.timeDepart))
                 .sort((a, b) => a.timeDepart.split(":")[1] - b.timeDepart.split(":")[1] ),
    };
  });

const targetBusStop = () => {
  if (queryDir === "Out bound") 
    return bstopTo;
  if (queryDir === "City bound")
    return bstopFrom
}

const getUrlWhenNoSchedulesFound = () => {
  const queryParams = new URLSearchParams(location.search);
  console.log("QP", queryParams);
  console.log("BS FROM > ", bstopFrom)
  console.log("BS TO > ", bstopTo)
  if (bstopFrom === 1 && bstopTo){
    queryParams.set('from', bstopTo);
    console.log("AAAAAAAAAAAAAAAAAAAAAAAAA")
  }
    
  
    return `/browse?${queryParams.toString()}`;
}

  return (
    <main onClick={logState}>
      {noSchedulesFound && <p>Nothing found for this day. Switch to browse page <Link to={getUrlWhenNoSchedulesFound()}> Click me</Link> -</p>}
      {errorMsg && <p className="alert alert-danger">{errorMsg}</p>}
      {range.map((r) => (
        <div key={r.hour}>
          <div className="d-inline-block p-2">
            {String(r.hour).padStart(2, "0")}:00
          </div>
          <div className="d-inline-block p-2">
            {r.items.map((item, index) => (
              <ScheduleItem key={index} item={item} />
            ))}
          </div>
        </div>
      ))}
    </main>
  );
}


