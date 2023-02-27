import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  Button,
  Grid,
  Stack,
} from "@mui/material";
import { Container } from "@mui/system";


export function ViewRoom() {
  const [classroom, setClassroom] = useState({});
  const params = useParams();
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then(setClassroom);
  }, [params.id]);


  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{classroom.classroomName}</h1>
              <div>
                <span id="created-date">Sukurta: {classroom.createdDate}</span>
                </div>
              <div>
                <span id="modified-date">Paskutinį kartą redaguota: {classroom.modifiedDate}</span>
              </div>
            </header>
          </Grid>

          <Grid item sm={12}>
            <h4>Pastatas</h4>
            <p>{classroom.building}</p>
          </Grid>

          <Grid item sm={12}>
            <h4>Klasės aprašas</h4>
            <p>{classroom.description}</p>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={`/update-classroom/${classroom.id}`}>
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
