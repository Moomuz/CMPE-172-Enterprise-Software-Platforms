import React, { Component, Fragment, useEffect } from 'react'
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import '../../styles/EmployeeDashboard.css'

const api = axios.create({
  baseURL: 'http://localhost:8090/customers'
})

class AllowPasswordChange extends Component {
  constructor() {
    super()

    this.state = {
      requests: [],
      firstname: '',
      lastname: '',
      email: ''
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    axios.get('http://localhost:8090/customers/requests')
      .then(response => {
        console.log(response)
          this.setState({
            requests: response.data
          })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    console.log(this.state)
    axios
      .post('http://localhost:8090/customer/approve-request', this.state)
      .then((response) => {
        console.log(response)
        const {temp} = this.state
        this.setState({
          ...temp,
          status: response.data
        })
        console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  render() {
    const { firstname, lastname, email } = this.state
    
    if(this.state.status === 'Password for ' + this.state.firstname + ' ' + this.state.lastname + ' successfully changed') {
        return (
            <div>
                <div class='sectionTitle'>
                    <p>Password Change Requests</p>
                </div>

                <div>
                    <p class='passChangeApproved'>Password changed for {this.state.firstname} {this.state.lastname}!</p>
                </div>
            </div>
        )
    }

    return (
        <div>
          <div class='sectionTitle'>
            <p>Password Change Requests</p>
          </div>

          <table>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
            </tr>

            {this.state.requests.map(request =>
              <tr key={request.id}>
                <td>{request.firstname}</td>
                <td>{request.lastname}</td>
                <td>{request.email}</td>
              </tr>
            )}
          </table>

          <form 
              class='passChangeForm' 
              onSubmit={this.handleSubmit}
            >
              <label for='firstname'>First Name:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='text'
                id='firstname'
                name='firstname'
                // placeholder='your current password'
                value={firstname}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <label for='lastname'>Last Name:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='text'
                id='lastname'
                name='lastname'
                // placeholder='your new password'
                value={lastname}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <label for='email'>E-mail:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='email'
                id='email'
                name='email'
                // placeholder='your current password'
                value={email}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <br></br>

              <button class='submit' type='submit'>
                Submit
              </button>
            </form>
        </div>
    )
  }
}

export default AllowPasswordChange
