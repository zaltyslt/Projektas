import {
  Button,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ScheduleView() {
  const [schedule, setSchedule] = useState({});
  const [subjects, setSubjects] = useState([]);
  const params = useParams();


  useEffect(() => {
    fetch(`api/v1/schedules/schedule/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setSchedule(data);
        setSubjects(data.groups.program.subjectHoursList);
      });
  }, []);

  const asd = (value) => {
    
  }

  return (
    <div>
      <Container>
        <h3>{schedule.groups && schedule.groups.name}</h3>
        <h5>
          {schedule.schoolYear} m. {schedule.semester}
        </h5>
        <h6>{schedule.groups && schedule.groups.shift.name}</h6>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell colSpan={12}>
                  Programa:{" "}
                  {schedule.groups && schedule.groups.program.programName}
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell>Dalykas</TableCell>
                <TableCell align="center">Trukmė (val.)</TableCell>
                <TableCell align="center">Nesuplanuota (val.)</TableCell>
                <TableCell align="center" className="activity">
                  Veiksmai
                </TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {subjects.map((subject) => (
                <TableRow key={subject.id}>
                  <TableCell>{subject.subjectName}</TableCell>
                  <TableCell align="center">{subject.hours}</TableCell>
                  <TableCell align="center">
                    {
                      (subject.id in schedule.subjectIdWithUnassignedTime) ?
                      schedule.subjectIdWithUnassignedTime[subject.id] :
                      subject.hours
                    }
                  </TableCell>
                  <TableCell align="center" className="activity">
                    <Link
                      to={`/schedules/add-lesson/${subject.subject}`}
                      state={{
                        schedule: {schedule},
                        subject: {subject}
                      }}
                    >
                      <Button variant="contained">Planuoti</Button>
                    </Link>
                    <Button variant="contained" onClick={asd(subject)}>{subject.subject}</Button>

                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Container>
    </div>
  );
}
