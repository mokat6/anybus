import { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import BusStopTable from "../BusStopTable";
import { apiGetLineFull, apiDelLine } from "services/user.service.js"
import { lineInfoLabel } from "utils/myUtils";

export default function LinePage(){
    const[data, setData] = useState();
    const[activeRouteIndexArr, setActiveRouteIndexArr] = useState([0]);
    const [showingDistance, setShowingDistance] = useState(true);
    const [captionHeights, setCaptionHeights] = useState({})

    const {lineId} = useParams();
    const secondRowColor = "#dedede";
    const navigate = useNavigate();


    useEffect(()=>{
        apiGetLineFull(lineId)
        .then(response => {
            setData(response.data);
        })
    }, [])

    const logState = () => {
        console.log("data > ",data);
        console.log("caption heights > ",captionHeights);

    }
    
    const handleRoutesClick = (id) => {
        const index = data.routes.findIndex(el => id === el.id);

        if (!activeRouteIndexArr.includes(index))
            setActiveRouteIndexArr(og => [...og, index]);   //not sorted, the OG way
            // setActiveRouteIndexArr(og => [...og, index].sort());
        else 
            setActiveRouteIndexArr(og => {
        setCaptionHeights(og => {
            const newCaptionHeights = { ...og };
            delete newCaptionHeights[id];
            return newCaptionHeights;
        })

        return og.filter(i => i != index)
    });
    }

    const handleDeleteLine = () => {
        apiDelLine(lineId).then(
            () =>  navigate(`/admin-panel/lines/`)
        )
    }

 
    if (!data) return (<div>Loading...</div>)
    return(
        <main onDoubleClick={logState}>
            <div className="d-flex flex-row justify-content-between align-items-center">
            <h1>{data.info.name}</h1>
            <div className="d-flex gap-3">
                <div className="form-check form-switch">
                    <label className="form-check-label"> Show distance
                        <input className="form-check-input" type="checkbox" role="switch"  
                        onChange={(e)=>{setShowingDistance(e.target.checked)}} 
                        checked={showingDistance} />
                    </label>
                </div>
                <Link to={`/admin-panel/schedules/${lineId}`} className="btn btn-secondary">Go to Schedules</Link>

                <Link to={`/admin-panel/lines/${lineId}/edit`} state={data}
                    className="btn btn-primary">Edit</Link>

                <button className="btn btn-danger" onClick={handleDeleteLine}>Del {data.info.name}</button>

            </div>
            </div>

            {/* Line Info box */}
            <div className="row">
                <div className="col-12 col-md-6 order-md-2">
                <table>
                    <thead>
                    </thead>
                    <tbody>
                        {Object.keys(lineInfoLabel).map((infoKey, index) => (
                            <tr key={index}>
                              <td className="pe-3">{lineInfoLabel[infoKey]}</td><td>{typeof data.info[infoKey] === 'boolean' ? (data.info[infoKey]?"yes":"no") : data.info[infoKey]}</td>
                            </tr>                 
                        ))}
                        {
                            data.info.customNotes.map((note) => (
                                <tr key={note.id}>
                                    <td>{note.noteKey}</td>
                                    <td>{note.noteValue}</td>
                                </tr>
                            ))
                        }
                        
                    </tbody>
                </table>

                </div>
                <div className="col-12 col-md-6 order-md-1">
                    <h2>Route variations:</h2>  
                    <ul className="list-group list-group-numbered route-selector ">
                        {data.routes.map((row, index)=>(
                              <li className={`list-group-item my-cursor-pointer  list-group-item-light
                                ${activeRouteIndexArr.includes(index) ? "active" : ""}`} 
                                key={index}
                                onClick={handleRoutesClick.bind(null,row.id)}>
                                {row.routeNotes || "main route"}
                              </li>
                        ))}
                    </ul>
                </div>
            </div>
            <div className="row mt-2 pt-2" style={{background:secondRowColor}}>
                <div className="col ">
                    {activeRouteIndexArr.map(index =>   (
                        <BusStopTable key={index}  activeRoute={data.routes[index]} showingDistance={showingDistance} activeRouteIndexArr={activeRouteIndexArr} captionHeights={captionHeights} setCaptionHeights={setCaptionHeights} />    
                    )   )

                        
                    }
                    
                </div>
            </div>


        </main>
    )
}