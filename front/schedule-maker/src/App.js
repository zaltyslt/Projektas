import "./App.css";
import { HashRouter, Routes, Route } from "react-router-dom";
import { Menu } from "./components/Menu";

import { GroupList } from "./pages/groups/GroupList";
import { CreateGroup } from "./pages/groups/CreateGroup";
import { ViewGroup } from "./pages/groups/ViewGroups";
import { EditGroup } from "./pages/groups/EditGroup";

import { ModuleList } from "./pages/modules/ModuleList";
import { CreateModule } from "./pages/modules/CreateModule";
import { ViewModule } from "./pages/modules/ViewModule";
import { EditModule } from "./pages/modules/EditModule";

import { CreateProgram } from "./pages/programs/CreateProgram"
import { ViewProgram } from "./pages/programs/ViewProgram";
import { UpdateProgram } from "./pages/programs/UpdateProgram";
import { ProgramList } from "./pages/programs/ProgramList";

import { RoomList } from "./pages/rooms/RoomList";
import { CreateRoom } from "./pages/rooms/CreateRoom";
import { ViewRoom } from "./pages/rooms/ViewRoom";
import { UpdateClassroom } from "./pages/rooms/UpdateRoom";

import { ShiftList } from "./pages/shifts/ShiftList";
import { AddShift } from "./pages/shifts/AddShift";
import { ViewShift } from "./pages/shifts/ViewShift";
import { ModifyShift } from "./pages/shifts/ModifyShift";

import { SubjectList } from "./pages/subjects/SubjectList";
import { CreateSubject } from "./pages/subjects/CreateSubject";
import { ViewSubject } from "./pages/subjects/ViewSubject";
import { EditSubject } from "./pages/subjects/EditSubject";

import { TeacherList } from "./pages/teachers/TeacherList";
import { ViewTeacher } from "./pages/teachers/ViewTeacher";
import { CreateTeacher } from "./pages/teachers/CreateTeacher";
import { EditTeacher } from "./pages/teachers/EditTeacher";

import { ScheduleList } from "./pages/schedules/ScheduleList";
import { CreateSchedule } from "./pages/schedules/CreateSchedule";
import { ViewSchedule } from "./pages/schedules/ViewSchedule";

function App() {
  return (
    <div className="App">
      <HashRouter basename={process.env.PUBLIC_URL ? '' : '/schedule-maker'}>
        <Menu />
        <Routes >
          <Route path="/" element={<ScheduleList />}></Route>
          <Route path="/teachers" element={<TeacherList />}></Route>
          <Route path="/groups" element={<GroupList />}></Route>
          <Route path="/modules" element={<ModuleList />}></Route>
          <Route path="/subjects" element={<SubjectList />}></Route>
          <Route path="/shifts" element={<ShiftList />}></Route>
          {/* CLASSROOMS PATHAI */}
          <Route path='/rooms' element={<RoomList />}></Route>
          <Route path='/create-classroom' element={<CreateRoom />}></Route>
          <Route path='/classrooms/view-classroom/:id' element={<ViewRoom />}></Route>
          <Route path='/update-classroom/:id' element={<UpdateClassroom />} />
          {/* SHIFTS PATHAI */}
          <Route exact path="/add-shift" element={<AddShift/>} ></Route>
          <Route path="/view-shift/:id" element={<ViewShift/>} ></Route>
          <Route path="/modify-shift/:id" element={<ModifyShift/>} ></Route>
          {/* PROGRAMS PATHAI */}
          <Route path="/programs" element={<ProgramList />}></Route>
          <Route path="/create-program" element={<CreateProgram />}></Route>
          <Route path='/programs/view-program/:id' element={<ViewProgram />}></Route>
          <Route path='/update-program/:id' element={<UpdateProgram />} />
          {/* MODULES PATHAI */}
          <Route path="/modules/create" element={<CreateModule />} />
          <Route path="/modules/view/:id" element={<ViewModule />}></Route>
          <Route path="/modules/edit/:id" element={<EditModule />}></Route>

          <Route path="/subjects/create" element={<CreateSubject />} />
          <Route path="/subjects/view/:id" element={<ViewSubject />}></Route>
          <Route path="/subjects/edit/:id" element={<EditSubject />}></Route>

          <Route path="/groups/create" element={<CreateGroup />}></Route>
          <Route path="/groups/view/:id" element={<ViewGroup />}></Route>
          <Route path="/groups/edit/:id" element={<EditGroup />}></Route>
          {/* TEACHERS PATHAI */}
          <Route path="/teachers/view/:id" element={<ViewTeacher />}></Route>
          <Route path="/teachers/create" element={<CreateTeacher />}></Route>
          <Route path="/teachers/edit/:id" element={<EditTeacher />}></Route>

          {/*   SCHEDULE PATHS */}
          <Route path="/create-schedule" element={<CreateSchedule />}></Route>
          <Route path="/schedules/view" element={<ViewSchedule />}></Route>
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
