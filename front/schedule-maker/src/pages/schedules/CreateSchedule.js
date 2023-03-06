import {
    Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function CreateSchedule() {
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState("");
  const [schoolYear, setSchoolYear] = useState("");
  const [semester, setSemester] = useState("");

  useEffect(() => fetchGroups, []);

  const fetchGroups = () => {
    fetch("api/v1/group/get-active")
      .then((response) => response.json())
      .then(setGroups);
  };

  return (
    <div>
      <Container>
        <h3>Sukurti naują tvarkaraštį</h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth required>
                <InputLabel id="group-label">Gupės pavadinimas</InputLabel>
                <Select
                  label="Grupės pavadinimas"
                  labelId="group-label"
                  id="group"
                  value={selectedGroup}
                  onChange={(e) => setSelectedGroup(e.target.value)}
                >
                  {groups.map((group) => (
                    <MenuItem key={group.id} value={group}>
                      {group.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Mokslo metai"
                id="schoolYear"
                name="schoolYear"
                value={schoolYear}
                onChange={(e) => setSchoolYear(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Sesija"
                id="semester"
                name="semester"
                value={semester}
                onChange={(e) => setSemester(e.target.value)}
              ></TextField>
            </Grid>

            <Grid>
                
            </Grid>

            <Grid item sm={10}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained">
                  Išsaugoti
                </Button>

                <Link to="/">
                  <Button variant="contained">Grįžti</Button>
                </Link>
              </Stack>
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
