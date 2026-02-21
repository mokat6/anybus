import Navbar from "./Navbar.js";
import React, { useEffect, useState } from 'react';
import { NavLink, Link, useLocation } from 'react-router-dom';
import { useNavigate, useSearchParams, useParams } from 'react-router-dom';
import { dateRegex, dateToString, validateInteger } from 'utils/myUtils'; 
  //https://reactdatepicker.com/
  import DatePicker from 'react-datepicker';
  import 'react-datepicker/dist/react-datepicker.css'; // Import CSS

  
  import AsyncSelect from "react-select/async";

  import AuthService from 'services/auth.service';
import { apiGetSelectBusStopSearchOptions, apiGetBusStopDtoFromAndTo } from "services/user.service.js";

//used for the two Asyncselect inputs
  let debounceTimeout;

  //prevents useEffect from changing URL if user hasen't touched any inputs. onChange functions on each form element make it false
let beforeUserInteraction = true;

let defaultOptionsPromise =  apiGetSelectBusStopSearchOptions("");


export default function Header() {
    //default search param values
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [busDir, setBusDir] = useState("Out bound");
    const [bstopFrom, setBstopFrom] = useState();
    const [bstopTo, setBstopTo] = useState();

    const [admin, setAdmin] = useState(undefined);
    const {lineId} = useParams(); //to see if i'm inside single_line_view

    const [searchParams] = useSearchParams();
   
    const location = useLocation();
    const currentPath = location.pathname;


const logState = () => {
  console.log("selectedDate > ", selectedDate);
  console.log("busDir > ", busDir);
  console.log("bstopFrom > ", bstopFrom);
  console.log("bstopTo > ", bstopTo);
}


  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if (user?.roles.includes("ROLE_ADMIN")) 
      setAdmin(user)
  },[])

  const navigate = useNavigate();


  //extract values from URL parameters, validating, and setting state
 useEffect(() => {
  fetchUrlData()
 }, [])


 
    const fetchUrlData = () => {
      let qBstopFrom = searchParams.get('from');
      let qbstopTo = searchParams.get('to');
      const queryDir = searchParams.get('dir');
      const queryDate = searchParams.get('date');
      
      //when no URL params, set FROM to response.data[0] from select search options
      if (!qBstopFrom && !qbstopTo && !queryDir && !queryDate) {
        defaultOptionsPromise.then(response => {
          if (response.data.length > 0) setBstopFrom(response.data[0])
        })
        return;
      }
      const bstopInteger = validateInteger(qBstopFrom);
          apiGetBusStopDtoFromAndTo(bstopInteger, validateInteger(qbstopTo)).then(response => {
          setBstopFrom(response.data.from)
          setBstopTo({...response.data.to, "value": response.data.to.value === null ? "" : response.data.to.value})
        })

        
    if (queryDir === "City bound")
      setBusDir(queryDir)
    else 
      setBusDir("Out bound")

    if (dateRegex.test(queryDate))
      setSelectedDate(new Date(queryDate))
    else 
    setSelectedDate(new Date())
    } //ends fetch URL data function


    useEffect(() => {
      if (bstopFrom?.value != 1)
        setBstopTo (null)
    }, [bstopFrom])

  useEffect(() => {
    if (beforeUserInteraction) return;

      // Construct your search parameters
      const queryParams = new URLSearchParams();
      queryParams.append('from', bstopFrom?.value ?? "");
      queryParams.append('to', bstopTo?.value ?? "");
      queryParams.append('dir', busDir);
      queryParams.append('date', selectedDate.toISOString().split("T")[0]);

      if (lineId) navigate(`/browse?${queryParams.toString()}`);
      else
      // Use navigate to update the URL with the search parameters
      navigate({ search: `?${queryParams.toString()}` });
  },[bstopFrom, bstopTo,busDir, selectedDate])

  //filters changed inside view_single_line_page, after filters change go back to browse





  //seems that for default select options if in main function which is async, then need to "return" data instead of callback, but when inside inner function then callback is needed
    const selectLoadOptions =  (inputValue, callback, second) => {
      clearTimeout(debounceTimeout);
      if (inputValue === "") {
        let func = async () => {
            const resolve = await defaultOptionsPromise;
            if (second)
              callback(resolve.data.filter(bstop => bstop.value != 1))
            else
              callback(resolve.data)
          }
          func();
        } else {
          debounceTimeout = setTimeout(async () => {

            let resolve = await apiGetSelectBusStopSearchOptions(inputValue);
            if (second)
              callback(resolve.data.filter(bstop => bstop.value != 1))
            else
              callback(resolve.data)
          }, 500); // Debouncer delay
        }
    }


  return (
    <div className="my-header">
    <Navbar  admin={admin} fetchUrlData={fetchUrlData} />

    <header className="container-fluid"  onDoubleClick={logState}>
     
      <div className="row p-4 gy-3 pb-3" >
          <div className="col-md-6">
          <AsyncSelect cacheOptions defaultOptions loadOptions={selectLoadOptions} value={bstopFrom} 
            onChange={(update)=> {
              setBstopFrom(update)
              beforeUserInteraction = false;
              }} name="busStopSelect" isClearable />
          </div>
      {
         !currentPath.startsWith("/browse") &&
        <>
          <div className="col-md-6">
          <AsyncSelect cacheOptions defaultOptions loadOptions={(query, callback) => selectLoadOptions(query, callback, true)} value={bstopTo} isDisabled={bstopFrom?.value != 1}
            onChange={(update)=> {
              setBstopTo(update)
              beforeUserInteraction = false;
              setBusDir("Out bound")
            }} isClearable={true} name="busStopSelect" placeholder={ bstopFrom?.value == 1 ? "Destination..." : ""} />
          </div>


          <div className="col-md-6 d-flex gap-2 align-items-center ">
          <button className="btn btn-primary" onClick={()=>{{
            beforeUserInteraction = false;
            setSelectedDate(new Date())
            }}} >Today</button>
          <DatePicker 
                      name='datePicker-linterNeedsNameOrId'
                      selected={selectedDate}
                      onChange={(date) => {
                        setSelectedDate(date)
                        beforeUserInteraction = false;
                      }}
                      withPortal
                      portalId="root-portal"
                    />
          </div>
          
               

      
{/*  Radio buttons - direction */}
          <div className="col-md-6 align-items-center d-flex">
            {/* first */}
              <div className="form-check form-check-inline">
                  <label className="form-check-label">{bstopFrom?.value === 1 ? "City bus" : "Toward Anyksciai"}
                    <input className="form-check-input" type="radio" name="busDirectionOptions" checked={busDir == "City bound"}
                    value="City bound" onChange={(e) => {
                      beforeUserInteraction = false;
                      setBusDir(e.target.value)
                      if (e.target.value === "City bound" && bstopFrom?.value === 1)
                        setBstopTo(null)
                    }} />
                  </label>
              </div>
              {/* second */}
              <div className="form-check form-check-inline">
                  <label className="form-check-label">Going out
                    <input className="form-check-input" type="radio" name="busDirectionOptions" checked={busDir == "Out bound"} 
                    value="Out bound" onChange={(e) => {
                      setBusDir(e.target.value)
                      beforeUserInteraction = false;
                      }} />
                  </label>
              </div>
          </div>
        </>


      }
        
          </div>
    </header>
    </div>
  );
}

