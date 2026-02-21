import { useEffect, useRef, useState } from "react"

export default function({data}) {
    const [dirKey, setDirKey] = useState("Out bound")
    const [maxWidth, setMaxWidth] = useState(0);
    const [maxHeight, setMaxHeight] = useState(0);

    const midCellRef = useRef();
    const componentHeightRef = useRef();

    const swapDir = () => setDirKey(og => og === "Out bound" ?  "City bound" : "Out bound")

    console.log()
    
    useEffect(()=>{
        // const width = midCellRef.current.getBoundingClientRect().width;
        const height = componentHeightRef.current.getBoundingClientRect().height;
        console.log("HEIGHT", height)
        // if (width > maxWidth) setMaxWidth(width)
        // console.log("!!!!!! height", height, "max", maxHeight)
        if (height > maxHeight) {
            setMaxHeight(height)
            console.log("hello");
        } else if (height < maxHeight)
            componentHeightRef.current.style.height = maxHeight + "px";
        
       
    }, [dirKey])
    

    return(
        <div ref={componentHeightRef} onClick={()=>console.log("dataaaa", data)} >
            <table className="table my-arrow-background caption-top"  onClick={swapDir}>
            <caption className="text-center">{data["notes"][dirKey]} </caption>
                <thead>
                    <tr>
                        <th></th>
                        <th></th>
                        <th ref={midCellRef}></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {
                        data[dirKey].map((row, index) => (
                            <tr key={index}>
                                <td style={{backgroundColor: dirKey === "City bound" ? "rgba(255, 255, 255, 0.5)" : ""}}></td>
                                {/* <td className={`${row.highlight?.dir === "Out bound" ? "text-primary fw-bolder":""} px-0`}>{row.timesForward.slice(0,5)}</td>
                                <td className={`${row.highlight ? "text-primary fw-bolder":""} `} >{row.bstop.name}</td>
                                <td className={`${row.highlight?.dir === "City bound" ? "text-primary fw-bolder":""} px-0`}>{row.timesBack.slice(0,5)}</td> */}
                                <td style={{opacity: dirKey === "Out bound" ? 1 : 0.4}}>{row.timeForward.slice(0,5)}</td>
                                <td >{row.bstop.name}</td>
                                <td style={{opacity: dirKey === "City bound" ? 1 : 0.4}}>{row.timeReturn.slice(0,5)}</td>
                                <td style={{backgroundColor: dirKey === "Out bound" ? "rgba(255, 255, 255, 0.5)" : ""}}></td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>

    )
}