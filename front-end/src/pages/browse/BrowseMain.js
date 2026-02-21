import { useEffect, useState, useRef } from "react"
import { apiGetLinesPreviewPublic } from "services/user.service"
import { Link, useSearchParams } from "react-router-dom";
import BrowseTableRow from "./BrowseTableRow";
import { validateInteger } from "utils/myUtils";


export default function BrowseMain() {
    const [data, setData] = useState()
    const [bstopFromId, setBstopFromId] = useState();
    const [th1w, setTh1w] = useState();
    const [th2w, setTh2w] = useState();
    const [th3w, setTh3w] = useState();

  //fixing table cell widths
    const th1 = useRef(null);
    const th2 = useRef(null);
    const th3 = useRef(null);

    useEffect(()=>{
      setTh1w(th1.current.getBoundingClientRect().width)
      setTh2w(th2.current.getBoundingClientRect().width)
      setTh3w(th3.current.getBoundingClientRect().width)
   
    },[])

    // captionRef.current.style.height = `${maxHeight}px`;




    useEffect(()=> {
      apiGetLinesPreviewPublic().then(response => setData(response.data))
    },[])

    const logState = () =>{
      console.log("data > ", data);
    }
    
    const [searchParams] = useSearchParams();


    useEffect(()=>{
      const qfrom = validateInteger(searchParams.get('from'));
      if (bstopFromId != qfrom)
        setBstopFromId(qfrom);
  },[searchParams])

    if (!data) return <main><table className="table "><thead><tr><th ref={th1} style={{width:th1w??"auto"}}>Line</th><th ref={th2} style={{width:th2w??"auto"}}>Goes to / comes from</th><th ref={th3} style={{width:th3w??"auto"}}>Show stops</th></tr></thead></table>loading...</main>
    return(
        <main onClick={logState}>
          {logState()}
          {
        }
             <table className="table " style={{width:"100%"}}>
            <thead>
                <tr>
                    <th ref={th1} style={{width:th1w??"auto"}}>Line</th>
                    <th ref={th2} style={{width:th2w??"auto"}}>Goes to / comes from</th>
                    <th ref={th3} style={{width:th3w??"auto"}}>Show stops</th>
                </tr>
            </thead>
            <tbody>
                {data.filter(line => !bstopFromId || bstopFromId == 1 
                || line.mainStops.map(obj=>validateInteger(obj.id)).includes(validateInteger(bstopFromId)) 
                // || line.extraStops.map(obj=>obj.id).includes(bstopFromId)                
                ).map((row, i)=>(
                    <BrowseTableRow row={row} key={row.id} isEvenRow={i%2 === 0} />
                ))}
                
              </tbody>
        </table>
        </main>
    )
}








