import "./App.css";
import { HashRouter, Routes, Route } from "react-router-dom";
import { Menu } from "./components/Menu";
import { RoomList } from "./pages/rooms/RoomList";
import { ModuleList } from "./pages/modules/ModuleList";
import { ShiftList } from "./pages/shifts/ShiftList";
import { AddShift } from "./pages/shifts/AddShift";
import { ViewShift } from "./pages/shifts/ViewShift";
import { ModifyShift } from "./pages/shifts/ModifyShift";
import { SubjectList } from "./pages/subjects/SubjectList";
import { TeacherList } from "./pages/teachers/TeacherList";
import { GroupList } from "./pages/groups/GroupList";
import { ProgramList } from "./pages/programs/ProgramList";
import { CreateRoom } from "./pages/rooms/CreateRoom";
import { ViewRoom } from "./pages/rooms/ViewRoom";
import { UpdateClassroom } from "./pages/rooms/UpdateRoom";


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
          {/* CLASSROOMS PATHAI */}
          <Route path='/rooms' element={<RoomList />}></Route>
          <Route path='/create' element={<CreateRoom />}></Route>
          <Route path='/classrooms/view/:id' element={<ViewRoom />}></Route>
          <Route path='/update/:id' element={<UpdateClassroom />} />
          {/* CLASSROOM PATHAI DONE */}
          <Route exact path="/add-shift" element={<AddShift/>} ></Route>
          <Route path="/view-shift/:id" element={<ViewShift/>} ></Route>
          <Route path="/modify-shift/:id" element={<ModifyShift/>} ></Route>
          <Route path="/rooms" element={<RoomList />}></Route>
          <Route path="/programs" element={<ProgramList />}></Route>
         
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
