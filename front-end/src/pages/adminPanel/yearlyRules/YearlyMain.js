import { useEffect, useState } from "react"
import { apiGetAllYearlyRules, apiPostYearlyRulesCombo } from "services/user.service";
import { Link } from "react-router-dom";

import RuleMenu from "./RuleMenu";
import StaticDates from "./StaticDates";
import FormulaPattern from "./FormulaPattern";

let typingDebounce = null;
let newId = -8331;
let timeoutId;
const getEmptyRule = () => ({id: newId--, pattern1Params: [], timePeriods: [] })
 
const getEmptyDateRange = () => ({id: newId--, startMonth: null, startDay: null, endMonth: null, endDay:null})
const getEmptyPatternParam = () => ({dayOfWeek: null, id: newId--, nthOccurenceEachMonth: null})

export default function  YearlyMain() {
    const [data, setData] = useState();
    const [selectedRule, setSelectedRule] = useState(0);
    const [validationOn, setValidationOn] = useState(false);
    const [showTopBarMsg, setShowTopBarMsg] = useState();
    const [schedules, setSchedules] = useState();

    useEffect(()=>{
        apiGetAllYearlyRules().then(response => {
            setData(response.data.rules)
            setSchedules(response.data.schedules)
            
        })
    },[])

    const loggingStuff = () => {
        console.log("data", data);
        console.log("selectedRule", selectedRule);
        console.log("schedules", schedules);
    }

const handleSelectRule = (index) => {
    setSelectedRule(index)
}

const handleChange = (newValue, path, key, debounceTime=200) =>{
    clearTimeout(typingDebounce);
    typingDebounce = setTimeout(() => {
  
        setData(ogData => {
            const dataCopy = JSON.parse(JSON.stringify(ogData));
            let currentStep = dataCopy[selectedRule]; //walk the path, deeper and deeper

            if (path !== null && path !== undefined){
                for (let step of path)
                    currentStep = currentStep[step];
            }
            console.log("handleChange new value: ", newValue)

            currentStep[key] = newValue;
            return dataCopy;
        });
    }, debounceTime); //debounce
}

const handleAddNewDate = () => {
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(og));
        const empty = getEmptyDateRange();
        dataCopy[selectedRule].timePeriods.push(empty)
        return dataCopy;
    })
}

const handleAddNewPatternParams = () => {
    console.log("beginning and end");
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(og));
        const empty = getEmptyPatternParam();
        dataCopy[selectedRule].pattern1Params.push(empty)
        return dataCopy;
    })

}

const handleDeletePatternParams = (delParams) => {
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(data))
        dataCopy[selectedRule].pattern1Params = 
                                dataCopy[selectedRule].pattern1Params
                                .filter(params => params.id !== delParams.id)
        return dataCopy;
    })
}

const handleDeleteDate = (delDate) => {
    setData(og => {
        const dataCopy = JSON.parse(JSON.stringify(data))
        dataCopy[selectedRule].timePeriods = 
                                dataCopy[selectedRule].timePeriods
                                .filter(date => date.id !== delDate.id)
        return dataCopy;
    })
}

const handleAddNewRule = () => {
    setData(og => ([...og, getEmptyRule() ]))
    //adjust selected item
    // setSelectedRule(og => ({...og, "index": data[type].length}))
    setSelectedRule(data.length)
}



//------------------------------------------------------------------------



const handleSubmit = () => {
    const dataCopy = JSON.parse(JSON.stringify(data))

    //if big main validation fails

    if (!isAllDataValid(dataCopy)){
        setValidationOn(true);
        msgRun("Input validation failed, check if everything's entered right", "danger", 3000)
        return;
    }

    //validation passed, calling post api
    setValidationOn(false)

    //map negative IDs to null
    dataCopy.forEach(rule => {
        if (rule.id < 0 ) rule.id = null;
        
        rule.timePeriods.forEach(dateRange => {
            if (dateRange.id < 0) dateRange.id = null
        })
        rule.pattern1Params.forEach(dateRange => {
            if (dateRange.id < 0) dateRange.id = null
        })
    })

    apiPostYearlyRulesCombo(dataCopy).then(response =>{
        setData(response.data)
        msgRun("Successfully saved to the database", "success", 3000)
    })
}

const msgRun = (msg, color, time) => {
    setShowTopBarMsg({msg:msg, color:color});
    clearTimeout(timeoutId);
    timeoutId = setTimeout(() => {
        setShowTopBarMsg();
    }, time); 
}

const isAllDataValid = (data) => {

        for (const rule of data) {
    
        if (!rule.periodName )
            return false;


        for (const dateRange of rule.timePeriods) {
            if (!dateRange.startMonth || !dateRange.startDay || !dateRange.endMonth || !dateRange.endDay) 
                return false;
        }

        for (const params of rule.pattern1Params) {
            if (!params.dayOfWeek || !params.nthOccurenceEachMonth) 
                return false;
        }
    }


    return true;
} 

//------------------------------------------------------------------------

const handleDelRule = () => {
    setData(og => (og.filter((rule, i) => selectedRule !== i)))
        //adjust current selection
        const selectedBefore = selectedRule;
        const ogLength = data.length;

        if (ogLength <= 1) 
            setSelectedRule(0);
        else if (ogLength - 1 === selectedBefore) 
            setSelectedRule(og => og - 1)
   
    
}

const getscheduleCount = () => {
    if (data.length === 0) return 0
 return schedules.find(x => x.ruleId == data[selectedRule].id)?.schedules.length ?? "0"
}


    //loading
    if (!data) return <main>loading...</main>
    loggingStuff();
    return (
        // <main onClick={loggingStuff} className="mt-3">
        <main onClick={loggingStuff} className="mt-3">
             
             {
                showTopBarMsg &&
                    <div className={`alert alert-${showTopBarMsg.color} `} role="alert">
                        {showTopBarMsg.msg}
                    </div>
               }


            <div className="row">
                <div className="col-md-4">
                    <RuleMenu data={data} selectedRule={selectedRule} handleSelectRule={handleSelectRule} handleAddNewRule={handleAddNewRule} validationOn={validationOn} />
            </div>{/* col */}

            <div className="col-md-8">
                <div className="d-flex justify-content-between mb-2">
                    <div>
                        {
                         selectedRule != null &&
                        <button className="btn btn-danger" onClick={handleDelRule} disabled={getscheduleCount() > 0}>Del rule</button>
                        }

                        {
                            data[selectedRule] && data[selectedRule].id > 0 && 
                            <Link to={`/admin-panel/yearly-rules/${data[selectedRule].id}`} className="btn btn-Light">
                                Used by Schedules <span className="badge text-bg-secondary"> {getscheduleCount()} </span>
                            </Link>
                        }

                    </div>
                    <button className="btn btn-success" onClick={handleSubmit}>Save</button>
                </div>
                {   
                    data[selectedRule]
                    &&
                    <StaticDates rule={data[selectedRule]} selectedRule={selectedRule} handleChange={handleChange} 
                    handleAddNewDate={handleAddNewDate} handleDeleteDate={handleDeleteDate} validationOn={validationOn} handleAddNewPatternParams={handleAddNewPatternParams} 
                    handleDeletePatternParams={handleDeletePatternParams} data={data} />
                }
                {/* {
                    selectedRule.type === "Formula pattern" && data[selectedRule.type].length > 0
                    &&
                    <FormulaPattern rule={data[selectedRule.type][selectedRule.index]} selectedRule={selectedRule} 
                    handleChange={handleChange} handleAddNewPatternParams={handleAddNewPatternParams} handleDeletePatternParams={handleDeletePatternParams} 
                    validationOn={validationOn} />
                } */}

            </div>
            </div> {/* row */}
        </main>
    )
}