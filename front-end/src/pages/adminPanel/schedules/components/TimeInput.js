import React, { useState } from 'react';

const TimeInput = ({defaultValue, handleChange, path, inputIndex, className}) => {
  const [time, setTime] = useState(defaultValue);
  const [invalidFormat, setInvalidFormat] = useState(false);
  const handleChangeLocal = (e) => {
    let input = e.target.value;
    
    // Remove any non-numeric characters except colon
    input = input.replace(/[^0-9:]/g, '');

    // Insert colon after the first two characters
    if (input.length >= 3 && input.charAt(2) !== ':') {
      input = input.slice(0, 2) + ':' + input.slice(2);
    }


    // Handle the case when the user types a single digit followed by a colon
    if (input.length === 2 && input.charAt(1) === ':') {
      // Insert leading zero before the first digit
      input = '0' + input;
    }

    if (input.length === 1 && input.charAt(0) === ':') {
        // Insert 2 leading zero before the colon
        input = '00' + input;
      }
    
      //deal with colon past 12:
      if (input.length > 3) {
            if (input.charAt(4) === ':')
                input = input.slice(0, 4);
            
            if (input.charAt(3) === ':')
                input = input.slice(0, 3) + input.slice(4)

      }

    //max 23:59
    if (input.length === 2) {
        if (input > 23) input = 23
    }

    if (input.length >= 4) {
        let [hours, mins] = input.split(":")
        if (hours > 23) hours = 23;
        if (mins > 59) mins = 59;
        input = `${hours}:${mins}`
    }
    
    // Update state
    setTime(input);
    setInvalidFormat(false);    


    if (validate(input)) {
        handleChange(input, path , inputIndex)
    }
    else 
    handleChange(null, path , inputIndex)
    
  };


  // const handleBlur = (e) => {
  //   let input = e.target.value;
  //   if (!validate(input)) {
  //     setInvalidFormat(true);
  //     handleChange(null, path , inputIndex)
  //   }
  // };

  //passes validation = true, fails = false
  const validate = (input) => /^\d{2}:\d{2}$/.test(input)


  return (
    <input
      style={{width:"100px"}}
      type="text"
      value={time}
      maxLength="5" // Restrict input to 5 characters (HH:MM)
      onChange={handleChangeLocal}
      // onBlur={handleBlur}
      placeholder="HH:MM"
      className={`${invalidFormat ? 'border border-danger' : ''} ${className}` }

    />
  );
};

export default TimeInput;