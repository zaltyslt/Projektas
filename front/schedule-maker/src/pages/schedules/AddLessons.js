import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  Button,
  Grid,
  Stack,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  Select,
  FormControl,
  InputLabel,

} from "@mui/material";
import { Container } from "@mui/system";
import "./Schedule.css";


export function AddLessons() {




  return (
    <div>
      <TableContainer component={Paper}>
        <Table aria-label="custom pagination table">
          <TableHead>
            <TableRow>
              <TableCell>Savaitė</TableCell>
              <TableCell>PIRMADIENIS</TableCell>
              <TableCell>ANTRADIENIS</TableCell>
              <TableCell>TREČIADIENIS</TableCell>
              <TableCell>KETVIRTADIENIS</TableCell>
              <TableCell>PENKTADIENIS</TableCell>
            </TableRow>
            <TableRow>
              <TableCell></TableCell>
              <TableCell>

                <TableHead>
                  <TableRow>
                    <TableCell>1</TableCell>
                    <TableCell>2</TableCell>
                    <TableCell>3</TableCell>
                    <TableCell>4</TableCell>
                    <TableCell>5</TableCell>
                    <TableCell>6</TableCell>
                    <TableCell>7</TableCell>
                    <TableCell>8</TableCell>
                    <TableCell>9</TableCell>
                    <TableCell>10</TableCell>

                  </TableRow>
                </TableHead>

              </TableCell>
              <TableCell>1234567891011121314</TableCell>
              <TableCell>1234567891011121314</TableCell>
              <TableCell>1234567891011121314</TableCell>
              <TableCell>1234567891011121314</TableCell>
            </TableRow>

          </TableHead>
          <TableBody>
            <TableRow>
              <TableCell>
              </TableCell>
              <TableCell >
                <Select label="Dalykas">
                </Select>
              </TableCell>
              <TableCell >
                <FormControl
                  fullWidth
                >
                  <InputLabel id="subject-label">
                  </InputLabel>
                  <Select
                    required
                    variant="outlined"
                    labelId="subject-label"
                    label="Dalykas"
                    name="subjectName"
                    label="subjectName"
                  >
                  </Select>
                </FormControl>
              </TableCell>
              <TableCell>
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell>Labas
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell>Labas
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
              <TableCell >
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  )
}


