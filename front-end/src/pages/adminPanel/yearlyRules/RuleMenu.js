import { faPlus, faTrashCan} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export default function RuleMenu({data, selectedRule, handleSelectRule, handleAddNewRule, validationOn}) {

    const types = Object.keys(data).sort().reverse();

    const allValid = (rule) => {
        if (!rule.periodName) return false;

        //all rules are either this or that
        // if (rule.typeOfYearlyRule === "Static dates")
        //     return !rule.timePeriods.some(date => !date.startMonth && !date.startMonth && !date.endMonth && !date.endDay)
        // if (rule.typeOfYearlyRule === "Formula pattern")
        //     return !rule.pattern1Params.some(date => !date.dayOfWeek || !date.nthOccurenceEachMonth)

        return true;
    }


return (
    <div>
        <h1 className="h2">Rules</h1>
        <ul  className="list-group list-group-numbered my-yearly-rules" >   
        {
                        data.map((rule, i) => (
                            <li  className={`list-group-item list-group-item-action
                            ${i === selectedRule ? "active" : ""}
                            ${validationOn && !allValid(rule) ? "my-valid-border" : ""}

                            `} aria-current="true"
                            onClick={() => handleSelectRule(i)}
                            key={rule.id}
                            > {rule.periodName} </li>
                        ))
                    }
                   <button className='btn' onClick={() => handleAddNewRule()}>
                        <FontAwesomeIcon icon={faPlus}  /> 
                        new
                    </button>
                </ul>

    </div>


)

    return(
        types.map((ruleType, index) => (
            <div key={index}>
                <h1 className="h2">{types[index]}</h1>
                <ul  className="list-group list-group-numbered my-yearly-rules" >
                    {
                        data[ruleType].map((rule, i) => (
                            <li  className={`list-group-item list-group-item-action
                            ${i === selectedRule.index && ruleType === selectedRule.type ? "active" : ""}
                            ${validationOn && !allValid(rule) ? "my-valid-border" : ""}

                            `} aria-current="true"
                            onClick={() => handleSelectRule(i, ruleType)}
                            key={rule.id}
                            > {rule.periodName} </li>
                        ))
                    }

                    <button className='btn' onClick={() => handleAddNewRule(types[index])}>
                        <FontAwesomeIcon icon={faPlus}  /> 
                        new
                    </button>
                    </ul >
                    
                    
            </div>
        ))
    )
}