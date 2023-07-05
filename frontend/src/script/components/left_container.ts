import { html, LitElement } from "lit";
import { customElement } from "lit/decorators.js";
import { Button } from "bootstrap";

@customElement("left-container")
export class LeftContainer extends LitElement {    
    pButton = new Button(this);

    render() {
        return html`
            <h1> Left Container </h1>
            <pButton class="btn btn-primary"> test </pButton>
        `;
    }
}