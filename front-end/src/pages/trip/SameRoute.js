

const HIGHTLIGHT_COLOR = "#80bad9";

export default function SameRoute({data}){
console.log("ASDASDASDASASDASD", data)
    return(
        <div>
            <table className="table my-arrow-background" >
                <thead>
                    <tr>
                    
                    </tr>
                </thead>
                <tbody>
                    {
                        data.data.map((row, index) => (
                            <tr key={index}>
                                <td></td>
                                <td className={`${row.highlight?.dir === data.dir.firstHalf ? "text-primary fw-bolder":""} px-0`}>{row.timeFirstHalf.slice(0,5)}</td>
                                <td className={`${row.highlight ? "text-primary fw-bolder":""} `} >{row.bstop.name}</td>
                                <td className={`${row.highlight?.dir === data.dir.secondHalf ? "text-primary fw-bolder":""} px-0`}>{row.timeSecondHalf.slice(0,5)}</td>
                                <td></td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>


    )
}