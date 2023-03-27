import { Container } from "@mui/system";
import { Alert, Button, Grid, Stack, TextField } from "@mui/material";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { dateToUtc } from "../../helpers/helpers";
import "./Schedule.css";
import { BAD_SYMBOLS } from "../../helpers/constants";

export function EditHoliday() {
  const [holiday, setHoliday] = useState({});
  const [name, setName] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");
  const params = useParams();

  useEffect(() => {
    fetch(`api/v1/schedules/holiday/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setHoliday(data);
        setName(data.name);
      });
  }, []);

  return (
    <div>
      <Container>
        <h3>Atostogų redagavimas</h3>
        <Grid container rowSpacing={2} spacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              label="Pavadinimas"
              id="name"
              name="name"
              value={name}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Nuo"
                format="YYYY/MM/DD"
                value={dateFrom}
                
                
              ></DatePicker>
            </LocalizationProvider>
          </Grid>

          <Grid item sm={5}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                className="DatePicker"
                label="Iki"
                format="YYYY/MM/DD"
                value={dateUntil}
               
              ></DatePicker>
            </LocalizationProvider>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
