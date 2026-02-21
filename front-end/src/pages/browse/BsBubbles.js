import { useEffect, useRef, useState } from "react";
let autoWidth = true;

export default function BsBubbles({data}){
    const [boxWidth, setBoxWidth] = useState(0);
    const box = useRef()
    console.log("ASDADASDASDSD", data)
    useEffect(()=>{
        const width = box.current.getBoundingClientRect().width;
        if (width > boxWidth) setBoxWidth(width)
        else if (width != boxWidth) box.current.style.width = `${boxWidth}px`;
    })

    return(
        <div className="busStopList">
                                <ol ref={box}>
                                    {
                                      data.map((row, index) => (  
                                        <li key={index} className={row.highlight ? "text-primary" : ""}>
                                            <span style={{width:"3.5em"}}>{row.time}</span>
                                            <span className="ind-num">{index+1}</span>
                                            <span>{row.stop.name}</span>
                                        </li>
                                        ))
                                    }
                                    
                                </ol>
                            </div>
                            
    )
}