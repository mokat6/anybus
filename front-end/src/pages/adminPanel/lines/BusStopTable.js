import { useEffect, useRef } from "react";


export default function BusStopTable({activeRoute,  showingDistance,  captionHeights, setCaptionHeights}){
    const captionRef = useRef(null);

    useEffect(()=>{
        const height = captionRef.current.getBoundingClientRect().height;
        setCaptionHeights(og =>( {...og, [activeRoute.id]: height}))
    },[])

    useEffect(()=>{
        let maxHeight = Math.max(...Object.values(captionHeights))
        captionRef.current.style.height = `${maxHeight}px`;
    },[captionHeights])


      useEffect(() => {
        captionRef.current.style.height = "auto";
        const height = captionRef.current.getBoundingClientRect().height;

        setCaptionHeights(og =>( {...og, [activeRoute.id]: height}))
      }, [showingDistance])


    if (!activeRoute) return "The line has no routes yet";
    return (
        <table className="table d-inline-block ms-4 caption-top my-no-color-background" style={{width:"auto"}}>
          <caption ref={captionRef}>{activeRoute.routeNotes || "main"}</caption>
            <thead>
                <tr>
                <th>Name</th>
                {
                    showingDistance &&
                    <th>Distance</th>
                }

                </tr>
            </thead>
            <tbody>
                {activeRoute?.stopsArr.map((stop, index)=>(
                    <tr key={index}>
                        <td>{stop?.name}</td>
                       {
                        showingDistance &&
                        <td className="vertical-center">
                             {activeRoute.distanceMetersList[index]}
                         </td>
                       }
                        
                    </tr>
                ))}
            </tbody>
        </table>
    )
}

