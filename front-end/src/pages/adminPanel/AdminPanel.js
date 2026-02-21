import { Outlet } from "react-router-dom";
import SideBar from "pages/adminPanel/SideBar";

function AdminPanel(){


    return (
<div className="container-fluid">
  <div className="row">
    <div className="col-sm-2" style={{background:"#bdbdbd"}}>
    <SideBar />
    </div>
    

    <div className="col-sm-10">
        <Outlet />
    </div>
  </div>
</div>


    )


}


export default AdminPanel;