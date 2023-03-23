import {
  Button,
  Grid,
  Paper,
  Stack,
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

  return (
    <div>
      <Container>
        <Grid container rowSpacing={2}>
          <Grid item sm={12}>
            <h3>{schedule.groups && schedule.groups.name}</h3>
            <h5>
              {schedule.schoolYear} m. {schedule.semester}
            </h5>
            <h6>{schedule.groups && schedule.groups.shift.name}</h6>
          </Grid>

          <Grid item sm={12}>
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
                        {subject.subject in schedule.subjectIdWithUnassignedTime
                          ? schedule.subjectIdWithUnassignedTime[
                              subject.subject
                            ]
                          : subject.hours}
                      </TableCell>
                      <TableCell align="center" className="activity">
                        <Link
                          to={`/schedules/add-lesson/${subject.subject}`}
                          state={{
                            schedule: { schedule },
                            subject: { subject },
                          }}
                        >
                          <Button
                            id="plan-button-view-schedule"
                            variant="contained"
                          >
                            Planuoti
                          </Button>
                        </Link>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to="/">
                <Button id="back-button-view-schedule" variant="contained">
                  Grįžti
                </Button>
              </Link>
              <Link to={"/schedules/" + params.id}>
                <Button id="schedule-button-view-schedule" variant="contained">
                  Tvarkaraštis
                </Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
