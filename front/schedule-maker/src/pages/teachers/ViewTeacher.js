import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { Button, Grid, Stack } from "@mui/material";

import { Container } from "@mui/system";

export function ViewTeacher() {
  const [teacher, setTeacher] = useState([]);
  const [contacts, setContacts] = useState([]);
  const [subjects, setSubjects] = useState([]);
  const [teacherSubjects, setTeacherSubjects] = useState([]);
  const [foundSubjects, setFoundSubjects] = useState([]);
  const params = useParams();
  let navigate = useNavigate();

  const getTeacherData = async () => {
    fetch(`/api/v1/teachers/view?tid=${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setTeacher(data);
        setTeacherSubjects(data.subjectsDtoList)
        setContacts(data.contacts);
            });
      };
      
 

  const getSubjectsData = () => {
    fetch(`/api/v1/subjects`)
      .then((response) => response.json())
      .then((data) => {
        setSubjects(data);
        // console.log(subjects);
      });
  };

  const mapTeacherSubjects = () => {
    const newFoundSubjects = [];

    teacherSubjects.forEach(
      (teacherSubject) => {
        // const found = numbers.find(number => number > 3);
        const subject = subjects.find((s) => s.id === teacherSubject.subjectId);
        if (subject) {
          newFoundSubjects.push(subject);
        }
      });
      setFoundSubjects(newFoundSubjects);
      // console.log(newFoundSubjects);
      
  };

  useEffect(() => {
    getTeacherData();
  }, []);

  useEffect(() => {
    getSubjectsData();
  }, [teacher]);
  useEffect(() => {
    mapTeacherSubjects();
  }, [subjects]);

  // const filteredSubjectsArray = subjects.filter(o => teacher.subjectsDtoList.includes(o.id));

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{teacher.fName}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {teacher.dateMotified}
              </span>
            </header>
          </Grid>

          <Grid item sm={6}>
            {/* Kol kas vienintelis privalomas */}
            <h4>Mokytojo vardas</h4>
            <p>{teacher.fName}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Mokytojo pavardė</h4>
            <p>{teacher.lName && teacher.lName}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Nickname</h4>
            <p>{teacher.nickName && teacher.nickName}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Būsena</h4>
            <p>{teacher.active ? "Aktyvus" : "Ištrintas"}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Kontaktinis telefonas</h4>
            <p>{contacts.phoneNumber ? contacts.phoneNumber : "n"}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>El. paštas</h4>
            <p>{contacts.directEmail ? contacts.directEmail : "n"}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Teams vardas</h4>
            <p>{contacts.teamsName ? contacts.teamsName : "n"}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Teams el. paštas</h4>
            <p>{contacts.teamsEmail ? contacts.teamsEmail : "n"}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Valandos per savaitę</h4>
            <p>{teacher.workHoursPerWeek && teacher.workHoursPerWeek}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Galima pamaina</h4>
            <p>
              {teacher.teacherShiftDto
                ? teacher.teacherShiftDto.name
                : "niGaunu"}
            </p>
          </Grid>

          <Grid item sm={12}>
            <h4>Dėstomi dalykai</h4>
            {/* {teacher.subjectsDtoList && teacher.subjectsDtoList.length > 0 */}
            {foundSubjects.map((subjectItem, index) => {
              return <p key={index}>{subjectItem.name}</p>;
            })}
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={`/teachers/edit/${teacher.id}`}>
                <Button variant="contained">Redaguoti</Button>
              </Link>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
