import { useState, useEffect, withRouter } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import SingleTrip from "./SingleTrip";
import TripInfo from "./TripInfo";
import { apiGetScheduleAndSingleTrip, getTripInfo } from "services/user.service.js"
import SameRoute from "./SameRoute";
import { validateInteger } from "utils/myUtils";
import DiffRoute from "./DiffRoute";

let newId = -1234;

const isTime1Before2 = (str1, str2) => {
  const [hour1, min1] = str1.split(":")
  const [hour2, min2] = str2.split(":")
  if (hour1 - hour2 < 0) return true
  if (hour1 - hour2 > 0) return false
  if (min1 - min2 < 0) return true
  return false

}

//main
function Trip() {
  const { tripId } = useParams();
  const [errorMsg, setErrorMsg] = useState();
  const [data, setData] = useState();
  const [urlParams, setUrlParams] = useState();
  const [searchParams] = useSearchParams();
  const [bsFrom, setBsFrom] = useState();
  const [dir, setDir] = useState();
  const [lineInfo, setLineInfo] = useState();

  //URL params
  useEffect(()=>{
    let qFrom = searchParams.get('from')
    if (qFrom === null) setBsFrom(1);
    else {
      setBsFrom(validateInteger(searchParams.get('from')))
    }
   
    let qDir = searchParams.get('dir')
    setDir(qDir === "City bound" ? qDir : "Out bound");
    
    // setQFrom( validateInteger( ) || 1 );
    // setBstopTo( validateInteger(searchParams.get('to')) );
    // setQueryDir( searchParams.get('dir') === "City bound" ? "City bound" : "Out bound");

    // const qdate = searchParams.get('date');

    // if (dateRegex.test(qdate))
    //   setQueryDate(qdate)
    // else 
    //   setQueryDate(dateToString(new Date()))
    // // setQueryDate( searchParams.get('date') ?? dateToString(new Date()) );

  },[searchParams])

  
  useEffect(() => {
    apiGetScheduleAndSingleTrip(tripId).then(
      response => {
        setData(response.data.schedule)
        setLineInfo(response.data.lineInfo)    
      }, 
      error => setErrorMsg(error?.response?.data?.message || error.message || error.toString())
    )
  }, [])

  const logState = () => {
    console.log("data", data);
    console.log(getSameRouteTableData())
    console.log("bsFrom > ", bsFrom)
    console.log("dif route table data > ", getDiffRouteTableData());
  }

  const getSameRouteTableData = () => {
    let [left, right] = isTime1Before2(data.trips[0].timeList[0], data.trips[1].timeList[0]) ? [0,1] : [1,0]
     
    const tripLeft = data.trips[left]
    if (tripLeft.routeDirReversed) tripLeft.route.stopsArr.reverse()
    const tripRight = data.trips[right].timeList.reverse();

    const tableData = tripLeft.route.stopsArr.map((stop, index) => ({
        timeFirstHalf: tripLeft.timeList[index],
        bstop: stop,
        timeSecondHalf: tripRight[index],
        highlight: stop.id === bsFrom ? {dir: dir} : ""
    }))
    
    return {"data": tableData, "dir":{firstHalf: tripLeft.boundFor, secondHalf:data.trips[right].boundFor}};
  }

  const getDiffRouteTableData = () => {
    const outBound = data.trips.find(trip => trip.boundFor == "Out bound")
      if (outBound.routeDirReversed) outBound.route.stopsArr.reverse()
    const cityBound = data.trips.find(trip => trip.boundFor == "City bound");
      if (!cityBound.routeDirReversed) cityBound.route.stopsArr.reverse()
      
      cityBound.timeList.reverse();

      console.log("outbound", outBound)
      console.log("cityBound", cityBound)

      let reverse = false;
      let reverse2 = false;
      let toMaxInd = outBound.route.stopsArr.length - 1;
      let fromMaxInd = cityBound.route.stopsArr.length - 1;

      const revIndex = (index) => fromMaxInd - (toMaxInd - index);
      const revIndex2 = (index) => toMaxInd - (fromMaxInd - index);

      return {
        "Out bound": outBound.route.stopsArr.map((stop, index) => {
          let timeReturnVal;
          if (!reverse && stop.id === cityBound.route.stopsArr[index].id) {
            timeReturnVal = cityBound.timeList[index];
          } else if (!reverse) {
            timeReturnVal = '...';
            reverse = true;
          } else if (reverse && stop.id === cityBound.route.stopsArr[revIndex(index)]?.id){
            timeReturnVal = cityBound.timeList[revIndex(index)];
          } else if (reverse) {
            timeReturnVal = ""
          }

          return {
          timeForward: outBound.timeList[index],
          bstop: stop,
          timeReturn: timeReturnVal
        }
      }),
      "City bound": cityBound.route.stopsArr.map((stop, index) => {
        let timeForwardVal;
        if (!reverse2 && stop.id === outBound.route.stopsArr[index].id) {
          timeForwardVal = outBound.timeList[index];
        } else if (!reverse2) {
          timeForwardVal = '...';
          reverse2 = true;
        } else if (reverse2 && stop.id === outBound.route.stopsArr[revIndex2(index)]?.id){
          timeForwardVal = outBound.timeList[revIndex2(index)];
        } else if (reverse2) {
          timeForwardVal = ""
        }

        return {
        timeForward: timeForwardVal,
        bstop: stop,
        timeReturn: cityBound.timeList[index]
      }
    }),
    "notes": {"City bound": cityBound.route.routeNotes, "Out bound": outBound.route.routeNotes}
    }



  }

  // const getDiffRouteTableData = () => {
  //   const tableData = data.trips.map(trip => {
  //         let bstopArr = trip.route.stopsArr;
  //         if (trip.routeDirReversed) bstopArr.reverse();
      
  //        return {"dir": trip.boundFor,
  //        trip: bstopArr.map((stop, index) => ({
  //           bstop: stop,
  //           time: trip.timeList[index]
  //         }))
  //       }
      
  //   })
  
  //   return tableData;
  // }

  const isBothWaysSameRoute = () => {
    let id;
    for (const trip of data.trips) {
      if (id === undefined) id = trip.route.id;
      if (id !== trip.route.id) return false;      
    }
    return true;
  }

  if (errorMsg) return <p className="alert alert-danger">{errorMsg}</p>;
  if (!data) return <div>loading ... </div>;

  return (
  <main className="container" onClick={logState}>

    <div className="row">
      <div className='col-12 col-md-6'>
        <TripInfo lineInfo={lineInfo} data={data} />
      </div>
      <div className='col-12 col-md-6'>
        {
          isBothWaysSameRoute() 
          ?
          <SameRoute data={getSameRouteTableData()} />
          :
          <DiffRoute data={getDiffRouteTableData()} />
        }
        
        {/* <SingleTrip stops={data.stops} lineName={data.lineName} />  */}
      </div>
      <div className='col-12 col-md-6'>
        {/* <TripInfo data={data} />  */}
      </div>
    </div>
  </main>  
  );
}

export default Trip;
