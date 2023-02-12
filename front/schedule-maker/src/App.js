import "./App.css";
import { HashRouter, Routes, Route } from "react-router-dom";
import { Menu } from "./components/Menu";
import { RoomList } from "./pages/rooms/RoomList";
import { ModuleList } from "./pages/modules/ModuleList";
import { ShiftList } from "./pages/shifts/ShiftList";
import { AddShift } from "./pages/shifts/AddShift";
import { ViewShift } from "./pages/shifts/ViewShift";
import { SubjectList } from "./pages/subjects/SubjectList";
import { TeacherList } from "./pages/teachers/TeacherList";
import { GroupList } from "./pages/groups/GroupList";
import { ProgramList } from "./pages/programs/ProgramList";


function App() {
  return (
    <div className="App">
      <HashRouter>
        <Menu />

        <Routes>
          <Route path="/"></Route>
          <Route path="/teachers" element={<TeacherList />}></Route>
          <Route path="/groups" element={<GroupList />}></Route>
          <Route path="/modules" element={<ModuleList />}></Route>
          <Route path="/subjects" element={<SubjectList />}></Route>
          <Route path="/shifts" element={<ShiftList />}></Route>
          <Route path="/add-shift/:shiftID" element={<AddShift/>} ></Route>

          {/* <Route path="/subjects/edit/:id" element={<EditSubject />}></Route> */}

          <Route path="/view-shift" element={<ViewShift/>} ></Route>
          <Route path="/rooms" element={<RoomList />}></Route>
          <Route path="/programs" element={<ProgramList />}></Route>
         

        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
