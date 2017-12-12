using System;

namespace RoRClient.Commands.Base
{

    /// <summary>
    /// Basis-Interface für Commands
    /// </summary>
    public interface ICommand
    {
        void Execute();
    }
}
