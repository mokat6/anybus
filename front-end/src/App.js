import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "pages/home/Home.js";
import Trip from "pages/trip/Trip.js";
import Layout from "pages/layout/Layout.js";
import NoPage from "pages/nopage/NoPage.js";
import AdminPanel from "pages/adminPanel/AdminPanel.js";
import PublicHolidayManager from "pages/adminPanel/publicHoliday/PublicHolidayManager";
import AdminWelcome from "pages/adminPanel/AdminWelcome";
import YearlyMain from "pages/adminPanel/yearlyRules/YearlyMain";
import BusStopManager from "pages/adminPanel/busStops/BusStopManager";
import BusLines from "pages/adminPanel/lines/BusLines";
import LinePage from "pages/adminPanel/lines/LinePage/LinePage";
import LinePageEdit from "pages/adminPanel/lines/LinePage/LinePageEdit";
import Login from "authComponents/Login";
import SchLines from "pages/adminPanel/schedules/SchLines";
import SchMain from "pages/adminPanel/schedules/SchMain";
import SchedulesByRule from "pages/adminPanel/yearlyRules/SchedulesByRule";
import BrowseMain from "pages/browse/BrowseMain";
import ViewLineSchedules from "pages/browse/ViewLineSchedules";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="trip/:tripId" element={<Trip />} />

          <Route path="*" element={<NoPage />} />
          <Route path="/date/:date" element={<Home />} />
          
          <Route path="/browse" element={<BrowseMain />} />
          <Route path="/browse/:lineId/:schedId/:tripId" element={<ViewLineSchedules />} />
          <Route path="/browse/:lineId/" element={<ViewLineSchedules />} />

          <Route path="admin-panel" element={<AdminPanel />}>
            <Route index element={<AdminWelcome />} />

             <Route path="holidays" element={<PublicHolidayManager />} />
             <Route path="yearly-rules" element={<YearlyMain />} />
             <Route path="yearly-rules/:ruleId" element={<SchedulesByRule />} />
             
             <Route path="bus-stops" element={<BusStopManager />} />
             
             <Route path="lines" element={<BusLines />} />
             <Route path="lines/:lineId" element={<LinePage />} />
             <Route path="lines/:lineId/edit" element={<LinePageEdit />} />
             <Route path="lines/new" element={<LinePageEdit />} />
             
             <Route path="schedules" element={<SchLines />} />
             <Route path="schedules/:lineId" element={<SchMain />} />
             <Route path="schedules/:lineId/:openScheduleId" element={<SchMain />} />
          </Route>
          <Route path="/admin-access" element={<Login />} />

        </Route>
      </Routes>
    </BrowserRouter>
  );
}


export default App;
