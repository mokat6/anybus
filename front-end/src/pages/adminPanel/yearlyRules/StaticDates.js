import { useState } from 'react';
import { faPlus, faTrashCan} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import DatePick from './DatePick';

const occurencies = [{val:1, label:"first"}, {val:2, label:"second"}, {val:3, label:"third"}, {val:4, label:"fourth"}, {val:5, label:"fifth"}]
const daysOfWeek = [{val:"MONDAY", label:"Monday"}, {val:"TUESDAY", label:"Tuesday"}, {val:"WEDNESDAY", label:"Wednesday"}, {val:"THURSDAY", label:"Thursday"}, {val:"FRIDAY", label:"Friday"}, {val:"SATURDAY", label:"Saturday"}, {val:"SUNDAY", label:"Sunday"}]


export default function StaticDates({data, rule, selectedRule, handleChange, handleAddNewDate, handleDeleteDate, handleAddNewPatternParams, handleDeletePatternParams, validationOn}){

 
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

        <h1 className='h2'>Fixed period</h1>
        
        {
            rule.timePeriods.length === 0 &&
            <select className={`form-select`}
            value={rule.otherRuleId ?? ""}
            onChange={(e) => handleChange(e.target.value, [] , "otherRuleId", 0)} 
            >
            <option value="" key={-1523}></option>
            {
                data.filter(r=> r.id !== rule.id).map((option, index) => (
                    <option value={option.id} className="" key={index}>
                        {option.periodName}
                        </option>
                ))
            }
            </select>
        }




        <table className='table'>
            <thead>
                {
                    rule.timePeriods.length !== 0 &&
                    <tr>
                        <th>#</th><th>Date range</th>
                    </tr>
                }

            </thead>
            <tbody>
              {
                rule.timePeriods.map((dateRange, i) => (
                    <tr key={i}>
                        <td>{i + 1}</td>
                        <td className='d-flex gap-5'>
                            <div className={`flex-grow-1'  ${validationOn && (!dateRange.startMonth || !dateRange.startDay || !dateRange.endMonth || !dateRange.endDay) ? "my-valid-border" : ""}`}
                            style={{maxWidth:"240px"}} >
                                <DatePick dateRange={dateRange} handleChange={handleChange} index={i} />
                            </div>
                            <button className='btn' onClick={() => handleDeleteDate(dateRange)}><FontAwesomeIcon icon={faTrashCan} /> </button>
                        </td>
                    </tr>
                ))
              }
              <tr>
                <td colSpan={2}>
                    <button className='btn' onClick={handleAddNewDate} disabled={rule.otherRuleId} ><FontAwesomeIcon icon={faPlus} /> new</button>
                </td>
              </tr>
            </tbody>
        </table>


        <h1 className='h2 pt-3'>Formula pattern</h1>
        {/* ------------------- PAttern param-------------- */}

        <table className='table'>
            <thead>
                {
                    rule.pattern1Params.length !== 0 &&
                    <tr>
                        <th>#</th><th>Occurence <br/>in the month</th><th>Day of the week</th><th>Actions</th>
                    </tr>
                }

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


        {/* ---patern closed */}
    </div>
    )
}