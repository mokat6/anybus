import CreatableSelect from "react-select/creatable";
import { useEffect, useRef, useState } from "react";
import { faPlus, faMinus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { apiGetBusStopsAll, apiPostBusStopSave } from "services/user.service.js";

let newId = -123;
let flag = 1;
export default function BusStopTableEdit({
  activeRoute,
  handleChange,
  index,
  handleRemoveStop,
  handleAddStop,
  handleStopCountChange,
  validationOn
}) {
  const [allStops, setAllStops] = useState();
  const counter = useRef();

  const handleCreate = (newStopName, i) => {
    const newStop = {
      id: null,
      name: newStopName,
    };

     apiPostBusStopSave(newStop).then(response => {
        handleChange(response.data, ["routes", index, "stopsArr"], i); 
        flag ++;
     })
     
  };


  useEffect(() => {
    apiGetBusStopsAll().then((response) => setAllStops(response.data));
  }, [flag]);

  


  if (!activeRoute?.stopsArr.length || !allStops ) return;
  return (
    <table
      className="table d-inline-block ms-4 caption-top my-no-color-background"
      style={{ width: "auto"}}
    >
      <caption>
        <input
          defaultValue={activeRoute.routeNotes }
          name="routeNotes"
          placeholder="Via..."
          style={{ border: validationOn && !activeRoute.routeNotes? "red 3px dashed" : "" }}
          onChange={(e) =>
            handleChange(e.target.value, ["routes", index], "routeNotes")
          }
        />
        <input
          ref={counter}
          type="number"
          step="1"          
          min={activeRoute.stopsArr.findLastIndex((stop) => stop.id) + 1 || 1}
          defaultValue={activeRoute.stopsArr.length}
          className="float-end"
          style={{ width: "3em"}}
          onChange={(e) => {
            e.target.value = e.target.value.replace(/[^0-9]/g, '')
            if (!e.target.value) e.target.value = activeRoute.stopsArr.findLastIndex((stop) => stop.id) + 1 || 1
            handleStopCountChange(e.target.value, index)
          }}
        />
      </caption>
      <thead>
        <tr>
          <th className="ps-5">Name</th>
          <th>Distance</th>
        </tr>
      </thead>
      <tbody>
        {activeRoute?.stopsArr.map((stop, i) => (
          <tr key={i}>
                          {console.log(i)}

            <td className="d-flex align-items-center" style={{border: validationOn && !stop.name? "red 3px dashed" : "", minWidth:"200px"}}>
              <FontAwesomeIcon
                icon={faPlus}
                onClick={() => {handleAddStop(index, i, counter)
                }}
                className="btn btn-outline-secondary btn-small p-0"
              />
              <FontAwesomeIcon
                icon={faMinus}
                onClick={handleRemoveStop.bind(null, index, i, counter)}
                className="btn btn-outline-secondary btn-small p-0"
              />
              <CreatableSelect
                options={allStops.map((x) => ({ value: x, label: x.name + (x.coords ? "(" + x.coords +")":"") }))}
                value={{ label: stop.name, value: stop }}
                onChange={(newValue) =>
                  handleChange(
                    newValue.value,
                    ["routes", index, "stopsArr"],
                    i,
                    0
                  )
                }
                onCreateOption={(newStopName) => handleCreate(newStopName, i)}
                className={`${
                  stop.id < 0 ? "border border-success" : ""
                } flex-grow-1`}
              />
            </td>
            {i < activeRoute.stopsArr.length - 1 ? (
              <td className="vertical-center">
                <input
                  key={stop.id}  //without this key, weird rendering, douplicates
                  defaultValue={activeRoute.distanceMetersList[i]}
                  onChange={(e) => {
                    e.target.value = e.target.value.replace(/[^0-9]/g, '')
                    handleChange(e.target.value, ["routes", index, "distanceMetersList"], i)
                  }
                    
                  }
                  style={{ maxWidth: "77px" }}
                />
              </td>
            ) : (
              ""
            )}
          </tr>
        ))}
      </tbody>
    </table>
  );
}
