import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { apieGetSchedulesByRule } from "services/user.service";
import SchedulesByRuleRow from "./SchedulesByRuleRow";

export default function SchedulesByRule() {
    const {ruleId} = useParams();
    const [data, setData] = useState();
    
    useEffect(() => {
        apieGetSchedulesByRule(ruleId).then(response => {
            setData(response.data)
        })
    }, [])

    const logState = () => {
        console.log("data", data);
    }

    if (!data) return <main>loading...</main>
    return(
        <main onClick={logState}>

            <div className="accordion" id="accordionPanelsStayOpenExample">
            {
                data.map((lineRules, lineIndex) => (
            <div className="accordion-item" key={"a" + lineIndex}>
                <h2 className="accordion-header">
                <button className={`accordion-button collapsed`} type="button" data-bs-toggle="collapse" data-bs-target={`#panelsStayOpen-collapse${lineIndex}`}>
                    <span className="my-150px-span fw-bold">{lineRules.line.name}</span>
                    <span className="my-150px-span">{lineRules.line.routeStart}</span>
                    <span className="my-150px-span">{lineRules.line.routeEnd}</span>
                </button>
                </h2>
                <div id={`panelsStayOpen-collapse${lineIndex}`} className={`accordion-collapse collapse`}>
                <div className="accordion-body">


                <ul className="list-group list-group-flush">
                    {
                        lineRules.schedules.map((schedule, index) => (
                            <li className="list-group-item" key={index}>
                                <Link to={`/admin-panel/schedules/${lineRules.line.id}/${schedule.id}`}  className="text-dark">
                                    <SchedulesByRuleRow schedule={schedule} />
                                </Link>
                                

                            </li>
                        ))    
                    }
                    
                </ul>
                    
                    
                </div>
                </div>
            </div>
                ))
            }
            
            </div>



        </main>
    )
}