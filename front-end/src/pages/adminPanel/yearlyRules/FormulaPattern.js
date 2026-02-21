import { useState } from 'react';
import { faPlus, faTrashCan} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import DatePick from './DatePick';

const occurencies = [{val:1, label:"first"}, {val:2, label:"second"}, {val:3, label:"third"}, {val:4, label:"fourth"}, {val:5, label:"fifth"}]
const daysOfWeek = [{val:"MONDAY", label:"Monday"}, {val:"TUESDAY", label:"Tuesday"}, {val:"WEDNESDAY", label:"Wednesday"}, {val:"THURSDAY", label:"Thursday"}, {val:"FRIDAY", label:"Friday"}, {val:"SATURDAY", label:"Saturday"}, {val:"SUNDAY", label:"Sunday"}]


export default function StaticDates({rule, selectedRule, handleChange, handleAddNewPatternParams, handleDeletePatternParams, validationOn}){

    
    return(
        <div>
        <div className="input-group mb-3">
            <span className="input-group-text fs-4" id="basic-addon1">Name:</span>
            <input type="text" 
            className={`form-control fs-4" ${validationOn && !rule.periodName ? "my-valid-border" : ""} `}
            placeholder="Rule name..." aria-label="Rule name..." aria-describedby="basic-addon1" 
            defaultValue={rule.periodName}  key={rule.id}
            onChange={(e) => handleChange(e.target.value, [], "periodName", 100)}
            />
        </div>

    
        <table className='table'>
            <thead>
                <tr>
                    <th>#</th><th>Occurence <br/>in the month</th><th>Day of the week</th><th>Actions</th>
                </tr>
            </thead>
            <tbody>
              {
                rule.pattern1Params.map((rule, i) => (
                    <tr key={i}>
                        <td>{i + 1}</td>
                        <td>
                        <select className={`form-select ${validationOn && !rule.nthOccurenceEachMonth ? "my-valid-border" : ""}`}
                                value={rule.nthOccurenceEachMonth ?? ""}
                                onChange={(e) => handleChange(
                                    {...rule, nthOccurenceEachMonth: e.target.value} , ["pattern1Params"] , i, 0
                                )} 
                            >
                                 {/* empty */}
                                 { !rule.nthOccurenceEachMonth && <option value="" key={-997}></option> }
                                {
                                    occurencies.map((nth, i) => (
                                        <option value={nth.val} key={i}>{nth.label}</option>        
                                    ))
                                }
                            </select>
                        </td>
                        <td>
                        <select className={`form-select ${validationOn && !rule.dayOfWeek ? "my-valid-border" : ""}`}
                                value={rule.dayOfWeek ?? ""}
                                onChange={(e) => handleChange(
                                    {...rule, dayOfWeek: e.target.value} , ["pattern1Params"] , i, 0
                                )} 
                            >
                                {/* empty */}
                                { !rule.dayOfWeek && <option value="" key={-169}></option> }

                                {
                                    daysOfWeek.map((day, i) => (
                                        <option value={day.val} key={i}>{day.label}</option>        
                                    ))
                                }
                            </select>
                        </td>
                        <td>
                            <button className='btn' onClick={() => handleDeletePatternParams(rule)}><FontAwesomeIcon icon={faTrashCan} /> </button>
                        </td>
                    </tr>
                ))
              }
              <tr>
                <td colSpan={2}>
                    <button className='btn' onClick={handleAddNewPatternParams}><FontAwesomeIcon icon={faPlus} /> new</button>
                </td>
              </tr>
            </tbody>
        </table>
    </div>
    )
}