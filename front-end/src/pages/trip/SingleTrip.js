function SingleTrip({stops, lineName}) {


  return (
    <div>
      <h1> {lineName} - d </h1>
      <table className='table table-striped' style={{width: "auto"}}>
        <thead>

        </thead>
        <tbody>
            
      {stops.map(x=>(
      <tr key={x.id}>
        <td>{x.stopName}</td>
        <td>{x.timePoint}</td>
      </tr>
      ) )}
              </tbody>
        </table>
    </div>

  );
}

export default SingleTrip;
