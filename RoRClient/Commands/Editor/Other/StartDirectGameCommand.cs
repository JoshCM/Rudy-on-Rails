﻿using RoRClient.Commands.Base;
using RoRClient.Communication.DataTransferObject;
using RoRClient.Models.Game;
using RoRClient.Models.Session;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Commands.Editor.Other
{
    class StartDirectGameCommand : CommandBase
    {
        public StartDirectGameCommand(RoRSession session, MessageInformation message) : base(session, message)
        {

        }
        public override void Execute()
        {
            EditorSession.GetInstance().DeleteEditorSession();
            GameSession.GetInstance().Started = true;
        }
    }
}
