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
        setContacts(data.contacts);
        setTeacherSubjects(data.subjectsList)
        console.log(data);
            });
      };
  
  useEffect(() => {
    getTeacherData();
  }, []);

  
  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{teacher.fName +" "+ teacher.lName}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {teacher.dateModified}
              </span>
            </header>
          </Grid>

         

          <Grid item sm={12}>
            <h4>Kontaktinis telefonas</h4>
            <p>{contacts.phoneNumber ? contacts.phoneNumber : "n"}</p>
            </Grid>

            <Grid item sm={12}>
            <h4>El. paštas</h4>
            <p>{contacts.directEmail ? contacts.directEmail : "n"}</p>
          </Grid>
         
          <Grid item sm={12}>
            <h4>Teams vardas</h4>
            <p>{contacts.teamsName ? contacts.teamsName : "n"}</p>
            </Grid>

        <Grid item sm={12}>
            <h4>Teams el. paštas</h4>
            <p>{contacts.teamsEmail ? contacts.teamsEmail : "n"}</p>
          </Grid>
        
          <Grid item sm={12}>
            <h4>Valandos per savaitę</h4>
            <p>{teacher.workHoursPerWeek && teacher.workHoursPerWeek}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Galima pamaina</h4>
            <p>
              {teacher.teacherShiftDto
                ? teacher.teacherShiftDto.name
                : "Duomenys nepasiekiami"}
            </p>
          </Grid>

          <Grid item sm={12}>
            <h4>Dėstomi dalykai</h4>
            {/* {teacher.subjectsDtoList && teacher.subjectsDtoList.length > 0 */}
            {teacherSubjects.map((subjectItem, index) => {
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
